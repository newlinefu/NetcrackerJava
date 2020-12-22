package utils.sorters.bubble;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.sorters.ISorter;

import java.util.Comparator;

/**
 * @author Alexandr Smirnov
 * @param <T>
 */
public class BubbleSorter<T> implements ISorter<T> {

    private Logger logger;

    /**
     *
     */
    public BubbleSorter() {
        logger = LogManager.getLogger("Bubble sorter");
    }


    /**
     * @param comparator - Параметр сортировки
     * @param array - сортируемый массив
     * @return Мутированный отсортированный массив array
     */
    public T[] sort(Comparator<T> comparator, T[] array) {
        logger.info("Bubble sort started");
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

        logger.info("Bubble sort ended");
        return array;
    }
}
