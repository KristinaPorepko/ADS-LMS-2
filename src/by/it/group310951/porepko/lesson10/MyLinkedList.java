package by.it.group310951.porepko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация двусторонней очереди на основе двусвязного списка.
 * @param <E> тип элементов в деке
 */
public class MyLinkedList<E> implements Deque<E> {

    /**
     * Внутренний класс, представляющий узел двусвязного списка.
     * @param <E> тип данных, хранящихся в узле
     */
    private static class Node<E> {
        E data;         // Данные, хранящиеся в узле
        Node<E> next;   // Ссылка на следующий узел
        Node<E> prev;   // Ссылка на предыдущий узел

        /**
         * Конструктор узла.
         * @param data данные для хранения в узле
         * @param next ссылка на следующий узел
         * @param prev ссылка на предыдущий узел
         */
        Node(E data, Node<E> next, Node<E> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> head;   // Первый элемент списка
    private Node<E> tail;   // Последний элемент списка
    private int size;       // Количество элементов в списке

    /**
     * Конструктор по умолчанию. Создает пустую очередь.
     */
    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Возвращает строковое представление списка.
     * @return строка в формате [элемент1, элемент2, ...]
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Добавляет элемент в конец очереди.
     * @param element элемент для добавления
     * @return true (как указано в интерфейсе Collection)
     */
    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    /**
     * Удаляет элемент по указанному индексу.
     * @param index индекс удаляемого элемента
     * @return удаленный элемент
     * @throws IndexOutOfBoundsException если индекс выходит за границы списка
     */
    public E remove(int index) {
        checkIndex(index);
        Node<E> node = getNode(index);
        unlink(node);
        return node.data;
    }

    /**
     * Удаляет первое вхождение указанного элемента.
     * @param o элемент для удаления
     * @return true, если элемент был найден и удален
     */
    @Override
    public boolean remove(Object o) {
        Node<E> current = head;
        while (current != null) {
            if ((o == null && current.data == null) || (o != null && o.equals(current.data))) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Возвращает количество элементов в очереди.
     * @return размер очереди
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Добавляет элемент в начало очереди.
     * @param element элемент для добавления
     */
    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element, head, null);
        if (head != null) {
            head.prev = newNode;
        } else {
            tail = newNode;
        }
        head = newNode;
        size++;
    }

    /**
     * Добавляет элемент в конец очереди.
     * @param element элемент для добавления
     */
    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element, null, tail);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    /**
     * Возвращает первый элемент очереди без удаления.
     * @return первый элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    @Override
    public E element() {
        return getFirst();
    }

    /**
     * Возвращает первый элемент очереди без удаления.
     * @return первый элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    @Override
    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return head.data;
    }

    /**
     * Возвращает последний элемент очереди без удаления.
     * @return последний элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    @Override
    public E getLast() {
        if (tail == null) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return tail.data;
    }

    /**
     * Извлекает и удаляет первый элемент очереди.
     * @return первый элемент очереди или null, если очередь пуста
     */
    @Override
    public E poll() {
        return pollFirst();
    }

    /**
     * Извлекает и удаляет первый элемент очереди.
     * @return первый элемент очереди или null, если очередь пуста
     */
    @Override
    public E pollFirst() {
        if (head == null) {
            return null;
        }
        E data = head.data;
        unlink(head);
        return data;
    }

    /**
     * Извлекает и удаляет последний элемент очереди.
     * @return последний элемент очереди или null, если очередь пуста
     */
    @Override
    public E pollLast() {
        if (tail == null) {
            return null;
        }
        E data = tail.data;
        unlink(tail);
        return data;
    }

    // Вспомогательные методы

    /**
     * Удаляет указанный узел из списка.
     * @param node узел для удаления
     */
    private void unlink(Node<E> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
    }

    /**
     * Возвращает узел по указанному индексу.
     * @param index индекс узла
     * @return узел списка
     */
    private Node<E> getNode(int index) {
        Node<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    /**
     * Проверяет корректность индекса.
     * @param index проверяемый индекс
     * @throws IndexOutOfBoundsException если индекс некорректен
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // Остальные методы интерфейса Deque (не реализованы)
    @Override public boolean offerFirst(E e) { throw new UnsupportedOperationException(); }
    @Override public boolean offerLast(E e) { throw new UnsupportedOperationException(); }
    @Override public E removeFirst() { throw new UnsupportedOperationException(); }
    @Override public E removeLast() { throw new UnsupportedOperationException(); }
    @Override public E peekFirst() { throw new UnsupportedOperationException(); }
    @Override public E peekLast() { throw new UnsupportedOperationException(); }
    @Override public boolean removeFirstOccurrence(Object o) { throw new UnsupportedOperationException(); }
    @Override public boolean removeLastOccurrence(Object o) { throw new UnsupportedOperationException(); }
    @Override public boolean offer(E e) { throw new UnsupportedOperationException(); }
    @Override public E remove() { throw new UnsupportedOperationException(); }
    @Override public E peek() { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public void push(E e) { throw new UnsupportedOperationException(); }
    @Override public E pop() { throw new UnsupportedOperationException(); }
    @Override public boolean contains(Object o) { throw new UnsupportedOperationException(); }
    @Override public Iterator<E> iterator() { throw new UnsupportedOperationException(); }
    @Override public Iterator<E> descendingIterator() { throw new UnsupportedOperationException(); }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public void clear() { head = tail = null; size = 0; }

    /**
     * Тестовый метод для демонстрации работы MyLinkedList.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        System.out.println("=== Тестирование MyLinkedList ===");

        // Создаем и тестируем очередь
        MyLinkedList<String> deque = new MyLinkedList<>();

        // Добавление элементов
        System.out.println("\nДобавляем элементы:");
        deque.add("Первый");
        deque.addLast("Второй");
        deque.addFirst("Новый первый");
        System.out.println("Содержимое: " + deque);
        System.out.println("Размер: " + deque.size());

        // Получение элементов
        System.out.println("\nПолучаем элементы:");
        System.out.println("Первый элемент: " + deque.getFirst());
        System.out.println("Последний элемент: " + deque.getLast());

        // Удаление элементов
        System.out.println("\nУдаляем элементы:");
        System.out.println("Удален первый: " + deque.pollFirst());
        System.out.println("Удален последний: " + deque.pollLast());
        System.out.println("Содержимое: " + deque);

        // Тестирование исключений
        System.out.println("\nТестирование исключений:");
        try {
            System.out.println("Попытка получить первый элемент пустой очереди:");
            new MyLinkedList<String>().getFirst();
        } catch (NoSuchElementException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        // Очистка очереди
        System.out.println("\nОчищаем очередь:");
        deque.clear();
        System.out.println("Содержимое: " + deque);
        System.out.println("Размер: " + deque.size());

        System.out.println("\n=== Тестирование завершено ===");
    }
}