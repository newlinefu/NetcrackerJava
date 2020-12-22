package utils.validators;

import entities.contracts.Contract;


/**
 * Интерфейс для классов валидирования ксоздаваемых контрактов.
 */
public interface IValidator {


    /**
     * @param validatedElement - Валидируемый контракт
     * @return - Сообщение с результатом валидации
     */
    Message validate(Contract validatedElement);

}
