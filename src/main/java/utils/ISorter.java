package utils;

import java.util.Comparator;

/**
 *
 * @param <T>
 */
public interface ISorter<T> {


    /**
     *
     * @param array - Сортируемый массив
     * @param comparator - Условие сортировки исходного массива
     * @return Отсортированный исходный массив
     */
    T[] sort(T[] array, Comparator<T> comparator);

}
