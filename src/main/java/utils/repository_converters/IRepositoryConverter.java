package utils.repository_converters;

import entities.Repository;

/**
 * Интерфейс конвертирования репозитория из различных форматов
 * @author Alexandr Smirnov
 */
public interface IRepositoryConverter {

    /**
     *
     * @param filename Путь к файлу, содержимое которого будет конвертироваться
     * @param repos Дополняемый данными репозиторий
     */
    void parseFileData(String filename, Repository repos);

    /**
     *
     * @param data Строка, на основе которой будет строиться новый репозиторий
     * @param repos Дополняемый данными репозиторий
     */
    void parseStringData(String data, Repository repos);

}
