package utils.validators.validator_examples;

import entities.contracts.Contract;
import entities.contracts.DigitalTelevisionContract;
import utils.validators.IValidator;
import utils.validators.Message;
import utils.validators.Status;

/**
 * Класс проверки списка подключенных каналов у контракта
 * цифрового телевидения
 *
 * @author Smirnov Alexandr
 */
public class DigitalTelevisionChannelsValidator implements IValidator {

    /**
     * @param validatedElement - Валидируемый контракт
     * @return - Если колличество подключенных каналов равно 0 - возвращается сообщение об ошибке,
     * если меньше 3-х - предупреждение, а иначе сообщение об успешном прохождении валидации контрактом
     */
    @Override
    public Message validate(Contract validatedElement) {
        if (validatedElement instanceof DigitalTelevisionContract) {
            String[] channelPackage = ((DigitalTelevisionContract) validatedElement).getChannelPackage();
            if (channelPackage.length == 0) {
                return new Message(Status.ERROR, "Contract has empty channel package");
            } else if (channelPackage.length < 3) {
                return new Message(Status.WARNING, "Contract has less than 3 channels");
            } else {
                return new Message(Status.OK, "Success");
            }
        } else
            return new Message(Status.OK, "");
    }
}
