package utils.xml_actions.xml_entites;

import entities.Client;
import utils.xml_actions.adapters.LocalDateXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Тип для преобразования контрактов из xml
 * @author Alexandr Smirnov
 */
public class XmlContract {
    /**
     * ID контракта
     */
    @XmlElement
    public int id;

    /**
     * Начало действия
     */
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    public LocalDate startDate;

    /**
     * Дата окончания контракта
     */
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    public LocalDate endDate;

    /**
     * Клиент, к которому прикреплен контракт
     */
    @XmlElement
    public Client client;

    /**
     * Колличетство интернета в пакете (соответствует CellularContract)
     */
    @XmlElement
    public double internetMBQuantity;

    /**
     * Колличетство минут в пакете (соответствует CellularContract)
     */
    @XmlElement
    public int minutesQuantity;

    /**
     * Колличетство сообщений в пакете (соответствует CellularContract)
     */
    @XmlElement
    public int messagesQuantity;

    /**
     * Список каналов для данного контракта (соответствует DigitalTelevisionContract)
     */
    @XmlElement
    public String[] channelPackage;

    /**
     * Скорость интернета в контракте (соответствует WiredInternetContract)
     */
    @XmlElement
    public double maxKBInternetSpeed;
}
