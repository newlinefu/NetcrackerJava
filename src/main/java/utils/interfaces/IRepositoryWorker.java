package utils.interfaces;

import entities.Repository;

/**
 * Интерфейс класса взаимодействия между репозиториями и базой данных
 *
 * @author Alexandr Smirnov
 */
public interface IRepositoryWorker {

    /**
     *
     * @param repos - Репозиторий для сохранения
     */
    void save(Repository repos);

    /**
     *
     * @return Загруженный репозиторий
     */
    Repository restore();

}
