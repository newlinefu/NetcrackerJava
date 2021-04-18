package entities.contracts;

import entities.Client;
import utils.xml_actions.adapters.ContractXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Сущностный класс контракта.
 *
 * @author Alexandr Smirnov
 */
@XmlJavaTypeAdapter(ContractXmlAdapter.class)

public class Contract {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Client client;

    /**
     *
     * @return ID контракта в системе
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id - Измененное ID контракта
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     *
     * @return - Дата начала контракта
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate - Новая дата начала контракта
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return - Дата окончания контракта
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate - Новая дата окончания контракта
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return - Клиент, формивший данный контракт
     */
    public Client getClient() {
        return client;
    }

    /**
     *
     * @param client - Измененный клиент, оформивший данный контракт
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     *
     * @param id - ID контракта в системе
     * @param startDate - Дата начала контракта
     * @param endDate - Дата окончания контракта
     * @param client - КлиентЮ оформивший данный контракт
     */
    public Contract(int id, LocalDate startDate, LocalDate endDate, Client client) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
    }

    @Override
    public String toString() {
        return "Contract: id=( "
                + id
                + " ) "
                + "| startDate=( "
                + startDate
                + " ) "
                + "| endDate=( "
                + endDate
                + " ) "
                + "| client=( "
                + client
                + " )";
    }

}
