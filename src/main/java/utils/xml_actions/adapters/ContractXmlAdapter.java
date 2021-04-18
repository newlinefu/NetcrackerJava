package utils.xml_actions.adapters;

import entities.contracts.CellularContract;
import entities.contracts.Contract;
import entities.contracts.DigitalTelevisionContract;
import entities.contracts.WiredInternetContract;
import utils.xml_actions.xml_entites.XmlContract;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Класс-адаптер для преобразования контрактов в xml и обратно
 *
 * @author Alexandr Smirnov
 */
public class ContractXmlAdapter extends XmlAdapter<XmlContract, Contract> {
    /**
     *
     * @param xmlContract Контракт, полученный из xml документа
     * @return Результирующий контракт
     */
    @Override
    public Contract unmarshal(XmlContract xmlContract) throws Exception {
        if (xmlContract == null) {
            return null;
        }

        if (xmlContract.channelPackage != null) {
            return new DigitalTelevisionContract(
                    xmlContract.id,
                    xmlContract.startDate,
                    xmlContract.endDate,
                    xmlContract.client,
                    xmlContract.channelPackage
            );

        } else if (xmlContract.internetMBQuantity != 0
                    && xmlContract.minutesQuantity != 0
                    && xmlContract.messagesQuantity != 0) {
            return new CellularContract(
                    xmlContract.id,
                    xmlContract.startDate,
                    xmlContract.endDate,
                    xmlContract.client,
                    xmlContract.internetMBQuantity,
                    xmlContract.minutesQuantity,
                    xmlContract.messagesQuantity
            );
        } else {
            return new WiredInternetContract(
                    xmlContract.id,
                    xmlContract.startDate,
                    xmlContract.endDate,
                    xmlContract.client,
                    xmlContract.maxKBInternetSpeed
            );
        }
    }

    /**
     *
     * @param contract Имеющийся контракт
     * @return Контракт для преобразования в xml
     */
    @Override
    public XmlContract marshal(Contract contract) throws Exception {
        if (contract == null) {
            return null;
        }
        XmlContract xmlContract = new XmlContract();
        xmlContract.id = contract.getId();
        xmlContract.startDate = contract.getStartDate();
        xmlContract.endDate = contract.getEndDate();
        xmlContract.client = contract.getClient();
        if (contract instanceof CellularContract) {
            CellularContract cellularContract = (CellularContract) contract;
            xmlContract.messagesQuantity = cellularContract.getMessagesQuantity();
            xmlContract.internetMBQuantity = cellularContract.getInternetMBQuantity();
            xmlContract.minutesQuantity = cellularContract.getMinutesQuantity();
        } else if (contract instanceof DigitalTelevisionContract) {
            DigitalTelevisionContract digTelContract = (DigitalTelevisionContract) contract;
            xmlContract.channelPackage = digTelContract.getChannelPackage();
        } else {
            WiredInternetContract wirIntContract = (WiredInternetContract) contract;
            xmlContract.maxKBInternetSpeed = wirIntContract.getMaxKBInternetSpeed();
        }
        return xmlContract;
    }
}
