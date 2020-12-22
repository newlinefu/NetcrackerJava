package utils.sorters;

import java.util.Comparator;

/**
 *
 * @param <T>
 */
public interface ISorter<T> {

    /**
     * @param comparator - Параметр сортировки
     * @param array - Сортируемый массив
     * @return Отсортированный исходный массив
     */
    T[] sort(Comparator<T> comparator, T[] array);

}
