package utils.sorters.heap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.sorters.ISorter;

import java.util.Comparator;

/**
 * Класс-сортер пирамидальной сортировки.
 *
 * @author Alexandr Smirnov
 * @param <T>
 */
public class HeapSorter<T> implements ISorter<T> {

    private Logger logger;

    /**
     *
     */
    public HeapSorter() {
        logger = LogManager.getLogger("Heap sorter");
    }

    /**
     * Пирамидальная сортировка (HeapSort).
     *
     * @param array - Сортируемый массив
     * @return Отсортированный мутированный массив
     */
    @Override
    public T[] sort(Comparator<T> comparator, T[] array) {
        logger.info("Heap sort started");
        int len = array.length;

        //Построение сортирующего дерева
        for (int i = len / 2 - 1; i >= 0; i--) {
            heapify(comparator, array, len, i);
        }

        //Извлекаем корневые элементы
        for (int i = len - 1; i >= 0; i--) {
            if(comparator.compare(array[0], array[i]) > 0) {
                T dummy = array[0];
                array[0] = array[i];
                array[i] = dummy;
            }

            //Перегрупировываем сортирующее дерево
            heapify(comparator, array, i, 0);
        }
        logger.info("Heap sort ended");
        return array;
    }


    /**
     * Рекурсивная функция формирования сортируемого дерева. В таком дереве
     * каждый родитель должен быть больше (Если мы идем по возрастания условия компаратора)
     * своих потомков.
     *
     * @param comparator - Параметры сортировки
     * @param array - Сортируемый массив
     * @param len - Длина изменяемой в данный момент части
     * @param index - Индекс, с которого будет происходить формирования
     */

    private void heapify(Comparator<T> comparator, T[] array, int len, int index) {
        int largestElementIndex = index;
        int leftElementIndex = 2 * index + 1; // левый = 2*i + 1
        int rightElementIndex = 2 * index + 2; // правый = 2*i + 2

        if (leftElementIndex < len && comparator.compare(array[leftElementIndex], array[largestElementIndex]) > 0) {
            largestElementIndex = leftElementIndex;
        }

        if (rightElementIndex < len && comparator.compare(array[rightElementIndex], array[largestElementIndex]) > 0) {
            largestElementIndex = rightElementIndex;
        }

        // Если произошли изменения
        if (largestElementIndex != index) {
            T dummy = array[index];
            array[index] = array[largestElementIndex];
            array[largestElementIndex] = dummy;

            // Рекурсивно преобразуем в двоичную кучу затронутое поддерево
            heapify(comparator, array, len, largestElementIndex);
        }
    }
}
