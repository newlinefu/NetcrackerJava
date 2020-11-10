package utils.repository_converters;

import entities.Repository;

/**
 * Интерфейс конвертирования репозитория из различных форматов
 * @author Alexandr Smirnov
 */
public interface IRepositoryConverter {

    /**
     *
     * @param filename - Путь к файлу, содержимое которого будет конвертироваться
     * @return Новый репозиторий на основе данных из файла
     */
    Repository parseFileData(String filename);

    /**
     *
     * @param data - Строка, на основе которой будет строиться новый репозиторий
     * @return Новый репозиторий на основе тектовых данных
     */
    Repository parseStringData(String data);

}
