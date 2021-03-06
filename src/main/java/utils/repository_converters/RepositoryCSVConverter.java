package utils.repository_converters;

import annotations.Inject;
import entities.Client;
import entities.Repository;
import entities.contracts.CellularContract;
import entities.contracts.Contract;
import entities.contracts.DigitalTelevisionContract;
import entities.contracts.WiredInternetContract;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.validators.IValidator;
import utils.validators.Message;
import utils.validators.Status;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Класс конвертирования CSV формата в объект репозитория.
 *
 * @author Alexandr Smirnov
 */
public class RepositoryCSVConverter implements IRepositoryConverter {

    @Inject(IValidator.class)
    private List<IValidator> validators = new LinkedList<>();

    private Logger logger;

    /**
     *
     */
    public RepositoryCSVConverter() {
        logger = LogManager.getLogger("CSV convert");
    }

    /**
     * @param data  Строка в формате CSV, на основе которой будет строиться новый репозиторий. Если
     *              формат не будет соответствовать CSV, то будет построен пустой репозиторий
     * @param repos Дополняемый данными репозиторий
     */
    @Override
    public void parseStringData(String data, Repository repos) {
        Scanner stringScanner = new Scanner(data);
        parseAll(stringScanner, repos);
    }

    /**
     * @param filename - Путь к csv файлу, содержимое которого будет конвертироваться. Формат файла не обязан быть
     *                 с расширением CSV, если его содержимое будет пригодно для конвертирования. В случае
     *                 непригодности будет возвращен пустой репозиторий
     * @param repos    - Дополняемый данными репозиторий
     */
    @Override
    public void parseFileData(String filename, Repository repos) {

        logger.info(String.format("Get parsing file %s", filename));

        try {
            File csvDB = new File(filename);
            Scanner fileScanner = new Scanner(csvDB);

            logger.info(String.format("Start parsing file %s", filename));

            parseAll(fileScanner, repos);

            logger.info(String.format("End parsing file %s", filename));
        } catch (Exception err) {
            logger.info(err);
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    }


    private void parseAll(Scanner src, Repository repos) {
        List<Client> clients = new LinkedList<>();

        for (int i = 0; src.hasNextLine(); i++) {
            String csvContract = src.nextLine();

            //Первая строка отводится под аннотацию
            if (i > 0) {
                //parseLine возвращает null при битой строке. Репозиторий
                //не реагирует на добавление null
                Contract newContract = parseLine(csvContract, clients);
                Optional<Contract> validatedContract = validateContract(newContract);
                validatedContract.ifPresent(repos::add);
            }
        }
    }

    private Contract parseLine(String line, List<Client> clients) {
        String[] lineItems = line.split(";");

        //Поверхностная проверка на валидность строки
        if (!isValidContractData(lineItems)) {
            return null;
        }

        String beginDate = lineItems[1].trim();
        String endDate = lineItems[2].trim();
        String fullName = lineItems[3].trim();
        String birthDate = lineItems[4].trim();
        String passport = lineItems[5].trim();
        String gender = lineItems[6].trim();
        String conType = lineItems[7].trim();

        //Если тип контракта обычный контракт => необходимости в последнем столбце нет
        String addInfo = conType.equals(Contract.class.getSimpleName()) ? "" : lineItems[8].trim();

        //Если ID контракта невалидно => возврат -1
        int contractId = extractContractId(lineItems[0].trim());

        //Ранее не проверялись данные клиента. Будут проверяться в данной функции, и, если
        //данные битые => вернется null
        Client candidate = clientParse(clients, fullName, birthDate, passport, gender);

        //Если хотябы один аспект контракта невалиден => контракт не парсится
        if (contractId == -1 || candidate == null) {
            return null;
        }

        return contractParse(contractId, beginDate, endDate, candidate, conType, addInfo);
    }

    private Contract contractParse(
            int contractId,
            String beginDate,
            String endDate,
            Client client,
            String conType,
            String addInfo
    ) {

        //Аналогичное вышестоящим проверкам действие. Если можно распарсить => идет
        //возврат, если его не произошло => данные битые
        LocalDate contractLocalDateStart = parseDateFromString(beginDate);
        LocalDate contractLocalDateEnd = parseDateFromString(endDate);

        if (contractLocalDateStart == null || contractLocalDateEnd == null) {
            return null;
        }

        switch (conType) {
            case ("Contract"): {
                return new Contract(contractId, contractLocalDateStart, contractLocalDateEnd, client);
            }
            case ("WiredInternetContract"): {
                double maxKBInternetSpeed;
                try {
                    maxKBInternetSpeed = Double.parseDouble(addInfo);
                } catch (Exception err) {
                    return null;
                }
                return new WiredInternetContract(
                        contractId,
                        contractLocalDateStart,
                        contractLocalDateEnd,
                        client,
                        maxKBInternetSpeed
                );
            }
            case ("DigitalTelevisionContract"): {

                String[] channelPackage = addInfo.split("&");

                for (int i = 0; i < channelPackage.length; i++) {
                    channelPackage[i] = channelPackage[i].trim();
                }

                return new DigitalTelevisionContract(
                        contractId,
                        contractLocalDateStart,
                        contractLocalDateEnd,
                        client,
                        channelPackage
                );
            }
            case ("CellularContract"): {
                String[] data = addInfo.split("&");
                double internetMBQuantity;
                int minutesQuantity;
                int messagesQuantity;

                try {
                    internetMBQuantity = Double.parseDouble(data[0].trim());
                    minutesQuantity = Integer.parseInt(data[1].trim());
                    messagesQuantity = Integer.parseInt(data[2].trim());
                } catch (Exception err) {
                    return null;
                }

                return new CellularContract(
                        contractId,
                        contractLocalDateStart,
                        contractLocalDateEnd,
                        client,
                        internetMBQuantity,
                        minutesQuantity,
                        messagesQuantity
                );
            }
            default: {
                return null;
            }
        }
    }


    private Client clientParse(
            List<Client> clients,
            String fullName,
            String birthDate,
            String passport,
            String gender
    ) {
        //Если дата битая - считаем, что вся строка невалидная
        if (birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] dateItems = birthDate.split("-");
            LocalDate birth;

            //Если дата невалидная - также пропускаем строку
            try {
                birth = LocalDate.of(
                        Integer.parseInt(dateItems[0]),
                        Integer.parseInt(dateItems[1]),
                        Integer.parseInt(dateItems[2])
                );
            } catch (Exception err) {
                return null;
            }

            Client candidate = new Client(clients.size(), fullName, passport, birth, gender);

            //Если в списке клиентов такой уже существует => вернем уже существующий
            for (int i = 0; i < clients.size(); i++) {
                Client listItem = clients.get(i);
                if (listItem.equals(candidate)) {
                    return listItem;
                }
            }

            clients.add(candidate);
            return candidate;
        } else {
            return null;
        }
    }


    private int extractContractId(String strContractId) {
        try {
            return Integer.parseInt(strContractId);
        } catch (Exception err) {
            return -1;
        }
    }

    private boolean isValidContractData(String[] lineItems) {
        return (
                lineItems.length == 9
                        ||
                        (lineItems.length == 8 && lineItems[7].trim().equals(Contract.class.getSimpleName()))
        )
                &&
                (
                        lineItems[1].trim().matches("\\d{4}-\\d{2}-\\d{2}")
                                &&
                                lineItems[2].trim().matches("\\d{4}-\\d{2}-\\d{2}")
                );
    }


    private LocalDate parseDateFromString(String strDate) {
        try {
            String[] dateStrArray = strDate.split("-");
            LocalDate localDateParsed = LocalDate.of(
                    Integer.parseInt(dateStrArray[0]),
                    Integer.parseInt(dateStrArray[1]),
                    Integer.parseInt(dateStrArray[2])
            );
            return localDateParsed;
        } catch (Exception err) {
            return null;
        }
    }

    private Optional<Contract> validateContract(Contract c) {

        logger.info(String.format("Start validate contract %s", c));

        if(c == null) {
            logger.info("Empty contract");
            return Optional.empty();
        }

        for(IValidator validator : validators) {

            Message resultMessage = validator.validate(c);
            String info = String.format("Validate result %s contract: %s", c, resultMessage);

            logger.info(info);

            if (resultMessage.getStatus().name().equals(Status.ERROR.name())){
                return Optional.empty();
            }
        }

        return Optional.of(c);
    }
}
