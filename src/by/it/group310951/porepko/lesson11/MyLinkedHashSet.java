package by.it.group310951.porepko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Реализация множества с сохранением порядка добавления элементов.
 * @param <E> тип элементов множества
 */
public class MyLinkedHashSet<E> implements Set<E> {
    private Node<E>[] buckets;  // Массив корзин (бакетов) для хранения элементов
    private int size;          // Количество элементов в множестве
    private Node<E> head;      // Первый элемент в порядке добавления
    private Node<E> tail;      // Последний элемент в порядке добавления

    /**
     * Конструктор по умолчанию. Создает пустое множество.
     */
    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        buckets = new Node[16];  // Начальная емкость - 16 бакетов
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Внутренний класс для хранения элементов множества.
     * @param <E> тип элемента
     */
    private static class Node<E> {
        E item;               // Значение элемента
        Node<E> next;         // Ссылка на следующий элемент в бакете (для разрешения коллизий)
        Node<E> orderNext;    // Ссылка на следующий элемент в порядке добавления

        Node(E item, Node<E> next, Node<E> orderNext) {
            this.item = item;
            this.next = next;
            this.orderNext = orderNext;
        }
    }

    /**
     * Вычисляет индекс бакета для объекта.
     * @param o объект
     * @return индекс бакета
     */
    private int getIndex(Object o) {
        if (o == null) return 0;
        int hash = o.hashCode();
        return Math.abs(hash) % buckets.length;
    }

    /**
     * Возвращает строковое представление множества.
     * @return строка в формате [элемент1, элемент2, ...]
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.item);
            if (current.orderNext != null) {
                sb.append(", ");
            }
            current = current.orderNext;
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
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        head = null;
        tail = null;
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
     */
    @Override
    public boolean add(E e) {
        int index = getIndex(e);
        Node<E> current = buckets[index];

        // Проверка на дубликаты
        while (current != null) {
            if (e == null ? current.item == null : e.equals(current.item)) {
                return false;
            }
            current = current.next;
        }

        // Создание нового узла
        Node<E> newNode = new Node<>(e, buckets[index], null);
        buckets[index] = newNode;

        // Добавление в конец списка порядка
        if (head == null) {
            head = newNode;
        } else {
            tail.orderNext = newNode;
        }
        tail = newNode;
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
        int index = getIndex(o);
        Node<E> current = buckets[index];
        Node<E> prev = null;

        // Поиск элемента в бакете
        while (current != null) {
            if (o == null ? current.item == null : o.equals(current.item)) {
                // Удаление из бакета
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }

                // Удаление из списка порядка
                Node<E> orderPrev = null;
                Node<E> orderCurrent = head;
                while (orderCurrent != current) {
                    orderPrev = orderCurrent;
                    orderCurrent = orderCurrent.orderNext;
                }
                if (orderPrev == null) {
                    head = current.orderNext;
                } else {
                    orderPrev.orderNext = current.orderNext;
                }
                if (current == tail) {
                    tail = orderPrev;
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
        int index = getIndex(o);
        Node<E> current = buckets[index];
        while (current != null) {
            if (o == null ? current.item == null : o.equals(current.item)) {
                return true;
            }
            current = current.next;
        }
        return false;
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
        Node<E> current = head;
        while (current != null) {
            Node<E> next = current.orderNext;
            if (c.contains(current.item)) {
                remove(current.item);
                modified = true;
            }
            current = next;
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
        Node<E> current = head;
        while (current != null) {
            Node<E> next = current.orderNext;
            if (!c.contains(current.item)) {
                remove(current.item);
                modified = true;
            }
            current = next;
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
     * Тестовый метод для демонстрации работы MyLinkedHashSet.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        System.out.println("=== Тестирование MyLinkedHashSet ===");

        // Создаем множество
        MyLinkedHashSet<String> set = new MyLinkedHashSet<>();

        // Добавляем элементы
        System.out.println("\nДобавляем элементы:");
        System.out.println("add(\"Apple\"): " + set.add("Apple"));
        System.out.println("add(\"Banana\"): " + set.add("Banana"));
        System.out.println("add(\"Orange\"): " + set.add("Orange"));
        System.out.println("add(\"Apple\"): " + set.add("Apple")); // Дубликат
        System.out.println("Множество: " + set);
        System.out.println("Размер: " + set.size());

        // Проверяем наличие элементов
        System.out.println("\nПроверяем наличие элементов:");
        System.out.println("contains(\"Apple\"): " + set.contains("Apple"));
        System.out.println("contains(\"Grape\"): " + set.contains("Grape"));

        // Удаляем элементы
        System.out.println("\nУдаляем элементы:");
        System.out.println("remove(\"Banana\"): " + set.remove("Banana"));
        System.out.println("remove(\"Pear\"): " + set.remove("Pear")); // Несуществующий
        System.out.println("Множество после удаления: " + set);
        System.out.println("Размер: " + set.size());

        // Проверяем порядок элементов
        System.out.println("\nПроверяем порядок элементов:");
        System.out.println("Порядок: " + set);

        // Очищаем множество
        System.out.println("\nОчищаем множество:");
        set.clear();
        System.out.println("Множество после очистки: " + set);
        System.out.println("Размер: " + set.size());
        System.out.println("isEmpty(): " + set.isEmpty());

        // Тестируем добавление коллекции
        System.out.println("\nТестируем addAll:");
        java.util.List<String> fruits = java.util.Arrays.asList("Kiwi", "Mango", "Apple");
        System.out.println("addAll(fruits): " + set.addAll(fruits));
        System.out.println("Множество: " + set);

        // Тестируем retainAll
        System.out.println("\nТестируем retainAll:");
        java.util.List<String> retain = java.util.Arrays.asList("Apple", "Mango");
        System.out.println("retainAll(retain): " + set.retainAll(retain));
        System.out.println("Множество: " + set);

        System.out.println("\n=== Тестирование завершено ===");
    }
}