package by.it.group310951.porepko.lesson11;

import java.util.Iterator;
import java.util.Set;
import java.util.Collection;

/**
 * Реализация множества на основе хэш-таблицы.
 * @param <E> тип элементов множества
 */
public class MyHashSet<E> implements Set<E> {
    private static final int DEFAULT_CAPACITY = 16; // Начальная емкость массива
    private Node<E>[] table; // Хэш-таблица для хранения элементов
    private int size; // Количество элементов в множестве

    /**
     * Узел односвязного списка для хранения элементов с коллизиями.
     */
    private static class Node<E> {
        final E key; // Значение элемента
        final int hash; // Хеш-код элемента
        Node<E> next; // Ссылка на следующий узел

        Node(E key, int hash, Node<E> next) {
            this.key = key;
            this.hash = hash;
            this.next = next;
        }
    }

    /**
     * Конструктор по умолчанию.
     */
    @SuppressWarnings("unchecked")
    public MyHashSet() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Возвращает строковое представление множества.
     * @return строка в формате [элемент1, элемент2, ...]
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Node<E> node : table) {
            while (node != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(node.key);
                first = false;
                node = node.next;
            }
        }
        sb.append("]");
        return sb.toString();
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
     * Очищает множество.
     */
    @Override
    public void clear() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
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
     * Добавляет элемент в множество.
     * @param e элемент для добавления
     * @return true, если элемент был добавлен
     * @throws NullPointerException если элемент равен null
     */
    @Override
    public boolean add(E e) {
        if (e == null) throw new NullPointerException("Элемент не может быть null");

        int hash = hash(e);
        int index = indexFor(hash);
        Node<E> current = table[index];

        // Проверка на дубликаты
        while (current != null) {
            if (current.hash == hash && current.key.equals(e)) {
                return false;
            }
            current = current.next;
        }

        // Добавление нового узла
        table[index] = new Node<>(e, hash, table[index]);
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
        if (o == null) return false;

        int hash = hash(o);
        int index = indexFor(hash);
        Node<E> current = table[index];
        Node<E> prev = null;

        while (current != null) {
            if (current.hash == hash && current.key.equals(o)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Проверяет наличие элемента в множестве.
     * @param o искомый элемент
     * @return true, если элемент найден
     */
    @Override
    public boolean contains(Object o) {
        if (o == null) return false;

        int hash = hash(o);
        int index = indexFor(hash);
        Node<E> current = table[index];

        while (current != null) {
            if (current.hash == hash && current.key.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Вычисляет хеш-код для элемента.
     * @param key элемент
     * @return хеш-код
     */
    private int hash(Object key) {
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    /**
     * Вычисляет индекс в массиве для заданного хеш-кода.
     * @param hash хеш-код
     * @return индекс в массиве
     */
    private int indexFor(int hash) {
        return (table.length - 1) & hash;
    }

    // Не реализованные методы
    @Override public Iterator<E> iterator() { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }

    /**
     * Тестовый метод для демонстрации работы MyHashSet.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        System.out.println("=== Тестирование MyHashSet ===");

        // Создаем множество
        MyHashSet<String> set = new MyHashSet<>();

        // Добавляем элементы
        System.out.println("\nДобавляем элементы:");
        System.out.println("add(\"Apple\"): " + set.add("Apple"));
        System.out.println("add(\"Banana\"): " + set.add("Banana"));
        System.out.println("add(\"Apple\"): " + set.add("Apple")); // Дубликат
        System.out.println("Множество: " + set);
        System.out.println("Размер: " + set.size());

        // Проверяем наличие элементов
        System.out.println("\nПроверяем наличие элементов:");
        System.out.println("contains(\"Apple\"): " + set.contains("Apple"));
        System.out.println("contains(\"Orange\"): " + set.contains("Orange"));

        // Удаляем элементы
        System.out.println("\nУдаляем элементы:");
        System.out.println("remove(\"Banana\"): " + set.remove("Banana"));
        System.out.println("remove(\"Grape\"): " + set.remove("Grape")); // Несуществующий
        System.out.println("Множество после удаления: " + set);
        System.out.println("Размер: " + set.size());

        // Очищаем множество
        System.out.println("\nОчищаем множество:");
        set.clear();
        System.out.println("Множество после очистки: " + set);
        System.out.println("Размер: " + set.size());
        System.out.println("isEmpty(): " + set.isEmpty());

        // Проверяем обработку null
        System.out.println("\nПроверяем обработку null:");
        try {
            System.out.println("Попытка добавить null:");
            set.add(null);
        } catch (NullPointerException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("\n=== Тестирование завершено ===");
    }
}
