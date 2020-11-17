package utils;

import java.util.Comparator;

/**
 * @author Alexandr Smirnov
 * @param <T>
 */
public class BubbleSorter<T> implements ISorter<T> {

    /**
     *
     * @param array - сортируемый массив
     * @param comparator - Условие сортировки исходного массива
     * @return Мутированный отсортированный массив array
     */
    public T[] sort(T[] array, Comparator<T> comparator) {
        boolean isSorted = false;

        while(!isSorted) {
            isSorted = true;
            for(int i = 0; i < array.length - 1; i++) {
                if(comparator.compare(array[i], array[i + 1]) > 0) {
                    isSorted = false;
                    T dummy = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = dummy;
                }
            }
        }
        return array;
    }
}
