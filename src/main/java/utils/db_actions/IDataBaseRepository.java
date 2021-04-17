package utils.db_actions;

import entities.Repository;

/**
 * Интерфейс класса взаимодействия между репозиториями и базой данных
 *
 * @author Alexandr Smirnov
 */
public interface IDataBaseRepository {

    /**
     *
     * @param repos - Репозиторий для сохранения в базе данных
     */
    void save(Repository repos);

    /**
     *
     * @return Репозиторий, загруженный из базы данных
     */
    Repository restore();

}
