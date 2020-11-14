package utils.repository_converters;

import entities.Repository;
import entities.contracts.CellularContract;
import entities.contracts.Contract;
import entities.contracts.DigitalTelevisionContract;
import entities.contracts.WiredInternetContract;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;


public class RepositoryCSVConverterTest {

    IRepositoryConverter converter;

    @Before
    public void setUp() {
        converter = new RepositoryCSVConverter();
    }

    @Test
    public void convertEmptyStringTest() {
        String testCSV = "ID; BEG; END; FIO; BDATE; PASP; GENDER; TYPE; ADDINFO\n\n";
        Repository emptyRepos = new Repository();
        converter.parseStringData(testCSV, emptyRepos);
        Assert.assertEquals(emptyRepos.getLength(), 0);
    }

    @Test
    public void convertFullRightStringTest() {
        String testCSV = "ID; BEG; END; FIO; BDATE; PASP; GENDER; TYPE; ADDINFO\n" +
                "1; 2019-12-01; 2020-12-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; Contract;\n" +
                "2; 2015-11-01; 2020-11-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; CellularContract; 3000 & 400 & 200\n" +
                "3; 2020-07-01; 2021-08-01; Смирнов Александр Константинович; 2000-03-30; 2019 202020; МУЖ; DigitalTelevisionContract; TV 3 & HBO & STS\n" +
                "4; 2017-11-01; 2028-11-02; Зенина Зинаида Аркадьевна; 1998-03-03; 2017 112211; ЖЕН; WiredInternetContract; 5978\n";
        Repository fullRightRepository = new Repository();
        converter.parseStringData(testCSV, fullRightRepository);
        Contract[] contracts = fullRightRepository.getContracts();

        CellularContract cc = (CellularContract) contracts[1];
        DigitalTelevisionContract dc = (DigitalTelevisionContract) contracts[2];
        String[] channelDCPackage = dc.getChannelPackage();
        WiredInternetContract wic = (WiredInternetContract) contracts[3];

        Assert.assertTrue(
                contracts.length == 4
                        && contracts[0].getId() == 1
                            && contracts[0].getStartDate().equals(LocalDate.of(2019, 12, 1))
                            && contracts[0].getEndDate().equals(LocalDate.of(2020, 12, 2))
                                && contracts[0].getClient().getFullName().equals("Ярослав Курчатов Михайлович")
                                && contracts[0].getClient().getBirth().equals(LocalDate.of(1995, 3, 3))
                                && contracts[0].getClient().getPassport().equals("2017 111111")
                                && contracts[0].getClient().getGender().equals("МУЖ")

                        && cc.getId() == 2
                            && cc.getStartDate().equals(LocalDate.of(2015, 11, 1))
                            && cc.getEndDate().equals(LocalDate.of(2020, 11, 2))
                            && Math.abs(cc.getInternetMBQuantity() - 3000.0) <= 0.0001
                            && cc.getMinutesQuantity() == 400
                            && cc.getMessagesQuantity() == 200
                                && cc.getClient() == contracts[0].getClient()

                        && dc.getId() == 3
                            && dc.getStartDate().equals(LocalDate.of(2020, 7, 1))
                            && dc.getEndDate().equals(LocalDate.of(2021, 8, 1))
                            && channelDCPackage[0].equals("TV 3") && channelDCPackage[1].equals("HBO") && channelDCPackage[2].equals("STS")
                                && dc.getClient().getFullName().equals("Смирнов Александр Константинович")
                                && dc.getClient().getBirth().equals(LocalDate.of(2000, 3, 30))
                                && dc.getClient().getPassport().equals("2019 202020")
                                && dc.getClient().getGender().equals("МУЖ")

                        && wic.getId() == 4
                            && wic.getStartDate().equals(LocalDate.of(2017, 11, 1))
                            && wic.getEndDate().equals(LocalDate.of(2028, 11, 2))
                            && Math.abs(wic.getMaxKBInternetSpeed() - 5978.0) <= 0.00001
                                && wic.getClient().getFullName().equals("Зенина Зинаида Аркадьевна")
                                && wic.getClient().getBirth().equals(LocalDate.of(1998, 3, 3))
                                && wic.getClient().getPassport().equals("2017 112211")
                                && wic.getClient().getGender().equals("ЖЕН")
        );
    }

    @Test
    public void convertStringWithTheSameClientsTest() {
        String testCSV = "ID; BEG; END; FIO; BDATE; PASP; GENDER; TYPE; ADDINFO\n" +
                "1; 2019-12-01; 2020-12-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; Contract;\n" +
                "2; 2015-11-01; 2020-11-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; CellularContract; 3000 & 400 & 200\n" +
                "3; 2020-07-01; 2021-08-01; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; DigitalTelevisionContract; TV 3 & HBO & STS\n";
        Repository reposWithTheSameClients = new Repository();
        converter.parseStringData(testCSV, reposWithTheSameClients);
        Contract[] contracts = reposWithTheSameClients.getContracts();

        Assert.assertTrue(
                contracts.length == 3
                && contracts[0].getClient() == contracts[1].getClient()
                && contracts[1].getClient() == contracts[2].getClient()
        );
    }

    @Test
    public void convertWithBrokenDataTest() {
        String testCSV = "ID; BEG; END; FIO; BDATE; PASP; GENDER; TYPE; ADDINFO\n" +

                "1; 2019-12-01; 2020-12-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; Contract;\n" +

                "2b; 2019-12-01; 2020-12-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; Contract;\n" +
                "3; 2019-12-101; 2020-12-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; Contract;\n" +
                "4; 2019-12-01; 2020-12-102; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; Contract;\n" +
                "5; 2019-12-01; 2020-12-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; Contrac;\n" +
                "6; 2019-12-01; 2020-12-02; Ярослав Курчатов Михайлович; 1995-103-03; 2017 111111; МУЖ; Contract;\n" +


                "7; 2015-11-01; 2020-11-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; CellularContract; 3000 & 400 & 200\n" +

                "8; 2015-11-01; 2020-11-02; Ярослав Курчатов Михайлович; 1995-03-03; 2017 111111; МУЖ; CellularContract; 3000 & 400b & 200\n" +


                "9; 2020-07-01; 2021-08-01; Смирнов Александр Константинович; 2000-03-30; 2019 202020; МУЖ; DigitalTelevisionContract; TV 3 & HBO & STS\n" +


                "10; 2017-11-01; 2028-11-02; Зенина Зинаида Аркадьевна; 1998-03-03; 2017 112211; ЖЕН; WiredInternetContract; 5978\n" +

                "11; 2017-11-01; 2028-11-02; Зенина Зинаида Аркадьевна; 1998-03-03; 2017 112211; ЖЕН; WiredInternetContract; 59b78\n";

        Repository reposWithBrokenData = new Repository();
        converter.parseStringData(testCSV, reposWithBrokenData);

        Assert.assertTrue(
                reposWithBrokenData.getLength() == 4
                && reposWithBrokenData.get(1).isPresent()
                && reposWithBrokenData.get(7).isPresent()
                && reposWithBrokenData.get(9).isPresent()
                && reposWithBrokenData.get(10).isPresent()
        );
    }
}