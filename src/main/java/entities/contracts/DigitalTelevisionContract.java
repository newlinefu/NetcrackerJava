package entities.contracts;

import entities.Client;

import java.time.LocalDate;

/**
 * Сущностный класс контракта телевизионной связи.
 *
 * @author Alexandr Smirnov
 */
public class DigitalTelevisionContract extends Contract {

    private String[] channelPackage;

    /**
     *
     * @return - Массив названий каналов, положенных по контракту
     */
    public String[] getChannelPackage() {
        return channelPackage;
    }

    /**
     *
     * @param channelPackage - Новый массив названий каналов, положенных по контракту
     */
    public void setChannelPackage(String[] channelPackage) {
        this.channelPackage = channelPackage;
    }

    /**
     *
     * @param id - ID контракта
     * @param startDate - Дата начала контракта
     * @param endDate - Дата окончания контракта
     * @param client - Клиент, которому принадлежит контракт
     * @param channelPackage - Массив названий каналов, положенных по контракту
     */
    public DigitalTelevisionContract(int id, LocalDate startDate, LocalDate endDate, Client client, String[] channelPackage) {
        super(id, startDate, endDate, client);
        this.channelPackage = channelPackage;
    }

    @Override
    public String toString() {
        String primaryInfo = "DigitalTelevisionContract: id=( "
                + getId()
                + " ) "
                + "| startDate=( "
                + getStartDate()
                + " ) "
                + "| endDate=( "
                + getEndDate()
                + " ) "
                + "| client=( "
                + getClient().toString()
                + " ) ";

        StringBuilder sb = new StringBuilder();
        sb.append(" | channelPackage=( ");

        for (int i = 0; i < channelPackage.length; i++) {
            sb.append(channelPackage[i]);
            if (i != channelPackage.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(" )");

        return primaryInfo + sb.toString();
    }
}
