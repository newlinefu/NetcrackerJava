package entities.contracts;

import entities.Client;
import entities.contracts.Contract;

import java.time.LocalDate;

/**
 * Сущностный класс контракта проводного интернета
 * @author Smirnov Alexandr
 */
public class WiredInternetContract extends Contract {

    private double maxKBInternetSpeed;

    /**
     *
     * @return - Максимальная скорость интернета по контракту в KB
     */
    public double getMaxKBInternetSpeed() {
        return maxKBInternetSpeed;
    }

    /**
     *
     * @param maxKBInternetSpeed - Новая максимальная скорость интернета по контракту в KB
     */
    public void setMaxKBInternetSpeed(double maxKBInternetSpeed) {
        this.maxKBInternetSpeed = maxKBInternetSpeed;
    }

    /**
     *
     * @param id - ID контракта
     * @param startDate - Дата начала контракта
     * @param endDate - Дата окончания контракта
     * @param client - Клиент, которому принадлежит данный контракт
     * @param maxKBInternetSpeed - Максимальная скорость интернета по контракту в KB
     */
    public WiredInternetContract(int id, LocalDate startDate, LocalDate endDate, Client client, double maxKBInternetSpeed) {
        super(id, startDate, endDate, client);
        this.maxKBInternetSpeed = maxKBInternetSpeed;
    }

    @Override
    public String toString() {
        return "WiredInternetContract: id=( " + getId() + " ) " +
                " | startDate=( " + getStartDate() + " ) " +
                " | endDate=( " + getEndDate() + " ) " +
                "| client=( " + getClient().toString() + " )" +
                " | maxKBInternetSpeed=( " + maxKBInternetSpeed + " )";
    }
}
