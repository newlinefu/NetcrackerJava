package utils;

import java.util.Comparator;

/**
 * @author Alexandr Smirnov
 * @param <T>
 */
public class BubbleSorter<T> implements ISorter<T> {

    private Comparator<T> comparator;

    /**
     *
     * @param comparator - Класс-реализация компаратора для сортируемых объектов/типов
     */
    public BubbleSorter(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     *
     * @param array - сортируемый массив
     * @return Мутированный отсортированный массив array
     */
    public T[] sort(T[] array) {
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
