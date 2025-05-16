package by.it.group310951.porepko.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// реализация списка на базе массива
public class ListA<E> implements List<E> {
    // начальный размер массива по умолчанию
    private static final int DEFAULT_CAPACITY = 10;
    // внутренний массив для хранения элементов
    private Object[] elements;
    // текущее количество элементов в списке
    private int size;

    public ListA() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    // увеличение массива при необходимости
    private void ensureCapacity() {
        if (size == elements.length) {
            // если массив заполнен, увеличиваем его в 2 раза
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    // возвращает строковое представление списка
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
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

    // добавляем элемент в конец списка
    @Override
    public boolean add(E e) {
        ensureCapacity();
        elements[size++] = e;
        return true;
    }

    // удаляем элемент по указанному индексу
    @Override
    public E remove(int index) {
        // проверка корректности индекса
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        @SuppressWarnings("unchecked")
        E removedElement = (E) elements[index];

        // сдвигаем все элементы после удаляемого на одну позицию влево
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null; // помогает сборщику мусора
        return removedElement;
    }

    // возвращает количество элементов в списке
    @Override
    public int size() {
        return size;
    }

    // возвращает итератор для обхода списка
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                @SuppressWarnings("unchecked")
                E element = (E) elements[currentIndex++];
                return element;
            }
        };
    }

    // нереализованные методы интерфейса List
    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public boolean contains(Object o) { throw new UnsupportedOperationException(); }

    @Override
    public Object[] toArray() { throw new UnsupportedOperationException(); }

    @Override
    public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }

    @Override
    public boolean remove(Object o) { throw new UnsupportedOperationException(); }

    @Override
    public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }

    @Override
    public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) { throw new UnsupportedOperationException(); }

    @Override
    public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }

    @Override
    public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }

    @Override
    public void clear() { throw new UnsupportedOperationException(); }

    @Override
    public E get(int index) { throw new UnsupportedOperationException(); }

    @Override
    public E set(int index, E element) { throw new UnsupportedOperationException(); }

    @Override
    public void add(int index, E element) { throw new UnsupportedOperationException(); }

    @Override
    public int indexOf(Object o) { throw new UnsupportedOperationException(); }

    @Override
    public int lastIndexOf(Object o) { throw new UnsupportedOperationException(); }

    @Override
    public ListIterator<E> listIterator() { throw new UnsupportedOperationException(); }

    @Override
    public ListIterator<E> listIterator(int index) { throw new UnsupportedOperationException(); }

    @Override
    public List<E> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }

    public static void main(String[] args) {
        ListA<String> list = new ListA<>();

        // добавление элементов
        list.add("first");
        list.add("second");
        list.add("third");
        System.out.println("После добавления: " + list);

        // размер списка
        System.out.println("Размер списка: " + list.size());

        // удаление элемента
        String removed = list.remove(1);
        System.out.println("Удалён элемент: " + removed);
        System.out.println("После удаления: " + list);

        // итератор
        System.out.println("Элементы через итератор:");
        for (String item : list) {
            System.out.println(item);
        }
    }
}