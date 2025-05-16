package by.it.group310951.porepko.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10; // начальный размер массива
    private Object[] elements; // внутренний массив для хранения элементов
    private int size; // текущее количество элементов


    public ListB() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    // увеличиваем емкость массива при необходимости
    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            // копируем элементы в новый массив
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

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

    @Override
    public boolean add(E e) {
        ensureCapacity();
        elements[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E removed = (E) elements[index];

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[--size] = null;
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    // вставляет элемент на указанную позицию
    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);
        ensureCapacity();

        // сдвиг элементов вправо
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        size++;
    }

    // удаляет первое вхождение указанного элемента
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if ((o == null && elements[i] == null) ||
                    (o != null && o.equals(elements[i]))) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    // заменяет элемент на указанной позиции
    @Override
    public E set(int index, E element) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }

    // проверяет пуст ли список
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // очищает список
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    // возвращает индекс первого вхождения элемента
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if ((o == null && elements[i] == null) ||
                    (o != null && o.equals(elements[i]))) {
                return i;
            }
        }
        return -1;
    }

    // возвращает элемент по индексу
    @Override
    public E get(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E element = (E) elements[index];
        return element;
    }

    // проверяет содержит ли список указанный элемент
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    // возвращает индекс последнего вхождения элемента
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if ((o == null && elements[i] == null) ||
                    (o != null && o.equals(elements[i]))) {
                return i;
            }
        }
        return -1;
    }

    // возвращает итератор для списка
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

    // вспомогательные методы
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // нереализованные методы
    @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(int index, Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public ListIterator<E> listIterator() { throw new UnsupportedOperationException(); }
    @Override public ListIterator<E> listIterator(int index) { throw new UnsupportedOperationException(); }
    @Override public List<E> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }


    public static void main(String[] args) {
        ListB<String> list = new ListB<>();
        System.out.println("\nДобавление элементов:");
        list.add("Первый");
        list.add("Второй");
        list.add(1, "Вставленный");
        System.out.println("Список: " + list);

        // получение элементов
        System.out.println("\nПолучение элементов:");
        System.out.println("Элемент с индексом 0: " + list.get(0));
        System.out.println("Элемент с индексом 2: " + list.get(2));

        // удаление элементов
        System.out.println("\nУдаление элементов:");
        System.out.println("Удален: " + list.remove(0));
        System.out.println("Список после удаления: " + list);

        // поиск элементов
        System.out.println("\nПоиск элементов:");
        System.out.println("Индекс 'Второй': " + list.indexOf("Второй"));
        System.out.println("Содержит 'Вставленный': " + list.contains("Вставленный"));

        // итератор
        System.out.println("\nИтерация по списку:");
        for (String item : list) {
            System.out.println(item);
        }

        // очистка
        System.out.println("\nОчистка списка:");
        list.clear();
        System.out.println("Пустой список: " + list);
        System.out.println("Размер: " + list.size());
    }
}