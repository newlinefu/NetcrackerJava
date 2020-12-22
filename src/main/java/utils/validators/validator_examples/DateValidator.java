package utils.validators.validator_examples;

import entities.contracts.Contract;
import utils.validators.IValidator;
import utils.validators.Message;
import utils.validators.Status;
import java.time.Period;

/**
 * Класс для проверки валидности даты начала контракта и даты его окончания.
 *
 * @author Alexandr Smirnov
 */
public class DateValidator implements IValidator{

    /**
     * @param validatedElement - Валидируемый контракт
     * @return Если разница даты конца контракта и даты начала отрицательная - возращает сообщение об ошибке,
     * если даты совпадают - возвращается предупреждения, в иных случаях - сообщение об успешном прохождении
     * валидации контрактом
     */
    @Override
    public Message validate(Contract validatedElement) {
        Period timeDelta = Period.between(validatedElement.getStartDate(), validatedElement.getEndDate());
        if(timeDelta.isNegative()) {
            return new Message(Status.ERROR, "Start date is later than end date");
        } else if(timeDelta.isZero()) {
            return new Message(Status.WARNING, "Start date and end date is equals");
        } else {
            return new Message(Status.OK, "Success");
        }
    }
}
