package utils.validators.validator_examples;

import entities.contracts.Contract;
import utils.validators.IValidator;
import utils.validators.Message;
import utils.validators.Status;

import java.time.LocalDate;
import java.time.Period;

/**
 * Класс, проверяющий валидность даты рождения клиента контракта
 *
 * @author Alexandr Smirnov
 */
public class ClientBirthValidator implements IValidator {

    /**
     * @param validatedElement - Валидируемый контракт
     * @return - Если дата рождения больше или равна акутальной - возвращает сообщение об ошибке. В случае,
     * если разница актуального времени и даты рождения меньше 16-ти лет (но проходит предыдущую проверку) возвращается
     * сообщение об ошибке (владельцем контракта не может быть несовершенно-летний). В других случаях возвращается
     * сообщение об успешном прохождении валидации
     */
    @Override
    public Message validate(Contract validatedElement) {
        Period yearsOld = Period.between(validatedElement.getClient().getBirth(), LocalDate.now());
        if(yearsOld.isNegative() || yearsOld.isZero()) {
            return new Message(Status.ERROR, "Client with invalid age");
        } else if(yearsOld.getYears() <= 16) {
            return new Message(Status.ERROR, "Attempting to add a contract with a minor client");
        } else {
            return new Message(Status.OK, "Success");
        }
    }
}
