package utils;

/**
 *
 * @param <T>
 */
public interface ISorter<T> {

    /**
     *
     * @param array - Сортируемый массив
     * @return Отсортированный исходный массив
     */
    public T[] sort(T[] array);

}
