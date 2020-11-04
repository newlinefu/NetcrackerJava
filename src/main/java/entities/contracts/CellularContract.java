package entities.contracts;

import entities.Client;
import entities.contracts.Contract;

import java.time.LocalDate;

/**
 * Сущностный класс контракта мобильной связи
 * @author Alexandr Smirnov
 */
public class CellularContract extends Contract {

    private double internetMBQuantity;
    private int minutesQuantity;
    private int messagesQuantity;

    /**
     *
     * @return Колличество МБ интернета по контракту
     */
    public double getInternetMBQuantity() {
        return internetMBQuantity;
    }

    /**
     *
     * @param internetMBQuantity - Измененное колличество МБ интернета по контракту
     */
    public void setInternetMBQuantity(double internetMBQuantity) {
        this.internetMBQuantity = internetMBQuantity;
    }

    /**
     *
     * @return - Колличество минут телефонных разговоров по контракту
     */
    public int getMinutesQuantity() {
        return minutesQuantity;
    }

    /**
     *
     * @param minutesQuantity - Новое колличество минут телефонных разговоров по контракту
     */
    public void setMinutesQuantity(int minutesQuantity) {
        this.minutesQuantity = minutesQuantity;
    }

    /**
     *
     * @return - Колличество SMS по контракту
     */
    public int getMessagesQuantity() {
        return messagesQuantity;
    }

    /**
     *
     * @param messagesQuantity - Новое колличество SMS по контракту
     */
    public void setMessagesQuantity(int messagesQuantity) {
        this.messagesQuantity = messagesQuantity;
    }

    /**
     *
     * @param id - ID Контракта
     * @param startDate - Дата начала контракта
     * @param endDate - Дата окончания контракта
     * @param client - Клиент контракта
     * @param internetMBQuantity - колличество МБ интернета по контракту
     * @param minutesQuantity - Колличество минут разговоров по контракту
     * @param messagesQuantity - Колличество SMS о контракту
     */
    public CellularContract(
            int id,
            LocalDate startDate,
            LocalDate endDate,
            Client client,
            double internetMBQuantity,
            int minutesQuantity,
            int messagesQuantity
    ) {
        super(id, startDate, endDate, client);
        this.internetMBQuantity = internetMBQuantity;
        this.minutesQuantity = minutesQuantity;
        this.messagesQuantity = messagesQuantity;
    }

    @Override
    public String toString() {
        return "CellularContract: id=( " + getId() + " ) " +
                " | startDate=( " + getStartDate() + " ) " +
                " | endDate=( " + getEndDate() + " ) " + "internetMBQuantity=( " + internetMBQuantity + " ) " +
                "| client=( " + getClient().toString() + " )" +
                " | minutesQuantity=( " + minutesQuantity + " ) " +
                " | messagesQuantity=( " + messagesQuantity + " )";
    }
}
