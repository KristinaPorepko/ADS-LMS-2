package by.it.group310951.porepko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Реализация множества на основе отсортированного массива.
 * Элементы хранятся в отсортированном порядке и поддерживают быстрый поиск.
 * @param <E> тип элементов множества (должен поддерживать Comparable)
 */
public class MyTreeSet<E> implements Set<E> {
    private static final int DEFAULT_CAPACITY = 16; // Начальная емкость массива
    private Object[] elements; // Массив для хранения элементов
    private int size; // Количество элементов в множестве

    /**
     * Конструктор по умолчанию. Создает пустое множество.
     */
    public MyTreeSet() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Увеличивает емкость массива при необходимости.
     */
    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length * 2;
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    /**
     * Бинарный поиск элемента в массиве.
     * @param o искомый элемент
     * @return индекс элемента, если найден; иначе -(insertion point + 1)
     */
    private int binarySearch(Object o) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            @SuppressWarnings("unchecked")
            Comparable<? super E> midVal = (Comparable<? super E>) elements[mid];
            int cmp = midVal.compareTo((E) o);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // Элемент найден
            }
        }

        return -(low + 1); // Элемент не найден
    }

    /**
     * Добавляет элемент в множество.
     * @param e элемент для добавления
     * @return true, если элемент был добавлен
     * @throws ClassCastException если элемент не поддерживает Comparable
     */
    @Override
    public boolean add(E e) {
        if (contains(e)) {
            return false; // Элемент уже существует
        }

        ensureCapacity();
        int index = binarySearch(e);

        if (index < 0) {
            index = -(index + 1); // Вычисляем позицию для вставки
        }

        // Сдвигаем элементы для вставки нового
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = e;
        size++;
        return true;
    }

    /**
     * Удаляет элемент из множества.
     * @param o элемент для удаления
     * @return true, если элемент был удален
     */
    @Override
    public boolean remove(Object o) {
        int index = binarySearch(o);
        if (index < 0) {
            return false; // Элемент не найден
        }

        // Сдвигаем элементы для удаления
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
        return true;
    }

    /**
     * Проверяет наличие элемента в множестве.
     * @param o искомый элемент
     * @return true, если элемент найден
     */
    @Override
    public boolean contains(Object o) {
        return binarySearch(o) >= 0;
    }

    /**
     * Возвращает количество элементов в множестве.
     * @return размер множества
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет, пусто ли множество.
     * @return true, если множество пусто
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Очищает множество.
     */
    @Override
    public void clear() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Возвращает строковое представление множества.
     * @return строка в формате [элемент1, элемент2, ...]
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Проверяет, содержатся ли все элементы коллекции в множестве.
     * @param c коллекция для проверки
     * @return true, если все элементы содержатся в множестве
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Добавляет все элементы коллекции в множество.
     * @param c коллекция для добавления
     * @return true, если множество изменилось
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Удаляет все элементы коллекции из множества.
     * @param c коллекция для удаления
     * @return true, если множество изменилось
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Оставляет в множестве только элементы, содержащиеся в указанной коллекции.
     * @param c коллекция элементов для сохранения
     * @return true, если множество изменилось
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                modified = true;
                i--;
            }
        }
        return modified;
    }

    // Не реализованные методы
    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Метод не реализован");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Метод не реализован");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Метод не реализован");
    }

    /**
     * Тестовый метод для демонстрации работы MyTreeSet.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        System.out.println("=== Тестирование MyTreeSet ===");

        // Создаем множество
        MyTreeSet<Integer> set = new MyTreeSet<>();

        // Добавляем элементы
        System.out.println("\nДобавляем элементы:");
        System.out.println("add(5): " + set.add(5));
        System.out.println("add(2): " + set.add(2));
        System.out.println("add(8): " + set.add(8));
        System.out.println("add(2): " + set.add(2)); // Дубликат
        System.out.println("Множество: " + set);
        System.out.println("Размер: " + set.size());

        // Проверяем порядок элементов (должны быть отсортированы)
        System.out.println("\nПроверяем порядок элементов:");
        System.out.println("Множество: " + set);

        // Проверяем наличие элементов
        System.out.println("\nПроверяем наличие элементов:");
        System.out.println("contains(2): " + set.contains(2));
        System.out.println("contains(10): " + set.contains(10));

        // Удаляем элементы
        System.out.println("\nУдаляем элементы:");
        System.out.println("remove(5): " + set.remove(5));
        System.out.println("remove(3): " + set.remove(3)); // Несуществующий
        System.out.println("Множество после удаления: " + set);
        System.out.println("Размер: " + set.size());

        // Тестируем работу с коллекциями
        System.out.println("\nТестируем addAll:");
        java.util.List<Integer> numbers = java.util.Arrays.asList(1, 3, 7, 2);
        System.out.println("addAll(numbers): " + set.addAll(numbers));
        System.out.println("Множество: " + set);

        // Тестируем retainAll
        System.out.println("\nТестируем retainAll:");
        java.util.List<Integer> retain = java.util.Arrays.asList(2, 3, 7);
        System.out.println("retainAll(retain): " + set.retainAll(retain));
        System.out.println("Множество: " + set);

        // Очищаем множество
        System.out.println("\nОчищаем множество:");
        set.clear();
        System.out.println("Множество после очистки: " + set);
        System.out.println("Размер: " + set.size());
        System.out.println("isEmpty(): " + set.isEmpty());

        System.out.println("\n=== Тестирование завершено ===");
    }
}