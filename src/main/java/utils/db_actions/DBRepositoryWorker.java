package utils.db_actions;

import entities.Client;
import entities.Repository;
import entities.contracts.CellularContract;
import entities.contracts.Contract;
import entities.contracts.DigitalTelevisionContract;
import entities.contracts.WiredInternetContract;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.interfaces.IRepositoryWorker;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Класс для преобразования данных между базой и объектами Repository
 *
 * @author Alexandr Sminov
 */
public class DBRepositoryWorker implements IRepositoryWorker {

    private static Logger logger = LogManager.getLogger("DataBase logger");

    private String url;
    private String driver;
    private String user;
    private String pass;

    public DBRepositoryWorker() {
        try {
            logger.info("Start get db props");

            InputStream dbp = DBRepositoryWorker.class.getClassLoader().getResourceAsStream("DBConnection.properties");
            Properties props = new Properties();
            props.load(dbp);

            url = props.getProperty("Database.DataURL");
            driver = props.getProperty("Database.Driver");
            user = props.getProperty("Database.Prop.user");
            pass = props.getProperty("Database.Prop.password");

            dbp.close();

            logger.info("Props has been taken");
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    private String insertingClientQuery = "insert into CONTR_CLIENT (\n" +
                                            "    CLIENT_ID," +
                                            "    CLIENT_FULL_NAME," +
                                            "    CLIENT_GENDER," +
                                            "    CLIENT_PASSPORT," +
                                            "    CLIENT_BIRTH" +
                                            ") values (?, ?, ?, ?, ?)";

    private String insertingContractQuery = "insert into CONTRACT (\n" +
                                            "    CONTRACT_ID," +
                                            "    START_DATE," +
                                            "    END_DATE," +
                                            "    CLIENT_ID," +
                                            "    TYPE_NAME," +
                                            "    ADDITIONAL_INFORMATION" +
                                            ") values (?, ?, ?, ?, ?, ?)";

    private String selectionContractQuery = "select CONTRACT_ID," +
                                            "START_DATE," +
                                            "END_DATE," +
                                            "TYPE_NAME," +
                                            "ADDITIONAL_INFORMATION," +
                                            "CC.CLIENT_ID," +
                                            "CLIENT_FULL_NAME," +
                                            "CLIENT_GENDER," +
                                            "CLIENT_PASSPORT," +
                                            "CLIENT_BIRTH" +
                                            "  from CONTRACT C" +
                                            "  left join CONTR_CLIENT CC" +
                                            "    on CC.CLIENT_ID = C.CLIENT_ID";

    /**
     *
     * @param repos - Репозиторий для сохранения в базе данных
     */
    @Override
    public void save(Repository repos) {
        try (
            Connection connection = DriverManager.getConnection(url, user, pass);
            PreparedStatement clientInsertPS = connection.prepareStatement(insertingClientQuery);
            PreparedStatement contractInsertPS = connection.prepareStatement(insertingContractQuery)
         ) {

            Contract[] contracts = repos.getContracts();
            for(int i = 0; i < contracts.length; i++) {
                Contract actualContract = contracts[i];
                Client actualClient = actualContract.getClient();

                clientInsertPS.setInt(1, actualClient.getId());
                clientInsertPS.setString(2, actualClient.getFullName());
                clientInsertPS.setString(3, actualClient.getGender());
                clientInsertPS.setString(4, actualClient.getPassport());
                clientInsertPS.setDate(5, Date.valueOf(actualClient.getBirth()));

                clientInsertPS.executeUpdate();

                contractInsertPS.setInt(1, actualContract.getId());
                contractInsertPS.setDate(2, Date.valueOf(actualContract.getStartDate()));
                contractInsertPS.setDate(3, Date.valueOf(actualContract.getEndDate()));
                contractInsertPS.setInt(4, actualClient.getId());
                contractInsertPS.setString(5, actualContract.getClass().getSimpleName());

                if(actualContract instanceof CellularContract) {
                    CellularContract cc = (CellularContract) actualContract;
                    String ccInfo = cc.getInternetMBQuantity() + "|"
                                    + cc.getMinutesQuantity() + "|"
                                    + cc.getMessagesQuantity();
                    contractInsertPS.setString(6, ccInfo);

                } else if(actualContract instanceof DigitalTelevisionContract) {

                    String channels = String.join("|", ((DigitalTelevisionContract) actualContract).getChannelPackage());
                    contractInsertPS.setString(6, channels);

                } else if(actualContract instanceof WiredInternetContract) {
                    WiredInternetContract wic = (WiredInternetContract) actualContract;
                    String maxKBInternetSpeed = Double.toString(wic.getMaxKBInternetSpeed());
                    contractInsertPS.setString(6, maxKBInternetSpeed);
                }

                contractInsertPS.executeUpdate();
                logger.info("Contract has been added: " + contracts[i]);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    /**
     *
     * @return Востановленный из базы репозиторий
     */
    @Override
    public Repository restore() {
        Repository resultedRepository = new Repository();
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement clientInsertPS = connection.prepareStatement(selectionContractQuery);
        ) {


            ResultSet reposData = clientInsertPS.executeQuery();
            while(reposData.next()) {

                String contractType = reposData.getString(4);

                Contract c;
                if(contractType.equals("WiredInternetContract")) {

                    c = new WiredInternetContract(
                        reposData.getInt(1),
                        reposData.getDate(2).toLocalDate(),
                        reposData.getDate(3).toLocalDate(),
                        new Client(
                                reposData.getInt(6),
                                reposData.getString(7),
                                reposData.getString(8),
                                reposData.getDate(10).toLocalDate(),
                                reposData.getString(9)
                        ),
                        Double.parseDouble(reposData.getString(5))
                    );

                } else if(contractType.equals("CellularContract")) {

                    String[] additionalCellConInfo = reposData.getString(5).split("\\|");
                    double internetMBQuantity = Double.parseDouble(additionalCellConInfo[0]);
                    int minutesQuantity = Integer.parseInt(additionalCellConInfo[1]);
                    int messagesQuantity = Integer.parseInt(additionalCellConInfo[2]);

                    c = new CellularContract(
                            reposData.getInt(1),
                            reposData.getDate(2).toLocalDate(),
                            reposData.getDate(3).toLocalDate(),
                            new Client(
                                    reposData.getInt(6),
                                    reposData.getString(7),
                                    reposData.getString(8),
                                    reposData.getDate(10).toLocalDate(),
                                    reposData.getString(9)
                            ),
                            internetMBQuantity,
                            minutesQuantity,
                            messagesQuantity
                    );

                } else {

                    c = new DigitalTelevisionContract(
                            reposData.getInt(1),
                            reposData.getDate(2).toLocalDate(),
                            reposData.getDate(3).toLocalDate(),
                            new Client(
                                    reposData.getInt(6),
                                    reposData.getString(7),
                                    reposData.getString(8),
                                    reposData.getDate(10).toLocalDate(),
                                    reposData.getString(9)
                            ),
                            reposData.getString(5).split("\\|")
                    );
                }

                resultedRepository.add(c);
                logger.info("Contract added: " + c);
            }
            return resultedRepository;
        } catch (SQLException ex) {
            logger.error(ex);
            return resultedRepository;
        }
    }
}
