package by.it.group310951.porepko.lesson10;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация двусторонней очереди (дека) на основе массива.
 * @param <E> тип элементов в деке
 */
public class MyArrayDeque<E> implements Collection<E> {
    private E[] data;          // Массив для хранения элементов
    private int currentSize;   // Текущее количество элементов
    private int head;          // Индекс первого элемента
    private int tail;          // Индекс следующего за последним элемента
    private static final int CAPACITY = 10;  // Начальная емкость массива

    /**
     * Конструктор по умолчанию. Инициализирует массив начальной емкостью.
     */
    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        data = (E[]) new Object[CAPACITY];
        currentSize = 0;
        head = 0;
        tail = 0;
    }

    /**
     * Возвращает строковое представление дека.
     * @return строка в формате [элемент1, элемент2, ...]
     */
    @Override
    public String toString() {
        if (currentSize == 0) {
            return "[]";
        }
        StringBuilder resultStr = new StringBuilder("[");
        int currentIndex = head;
        for (int i = 0; i < currentSize; i++) {
            resultStr.append(data[currentIndex]);
            if (i < currentSize - 1) {
                resultStr.append(", ");
            }
            currentIndex = (currentIndex + 1) % data.length;
        }
        resultStr.append("]");
        return resultStr.toString();
    }

    /**
     * Возвращает количество элементов в деке.
     * @return размер дека
     */
    @Override
    public int size() {
        return currentSize;
    }

    /**
     * Проверяет, пуст ли дек.
     * @return true если дек не содержит элементов
     */
    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Проверяет, содержится ли указанный элемент в деке.
     * @param obj искомый элемент
     * @return true если элемент найден
     */
    @Override
    public boolean contains(Object obj) {
        if (obj == null) {
            for (int i = 0; i < currentSize; i++) {
                int currentIndex = head + i;
                if (data[(currentIndex) % data.length] == null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentSize; i++) {
                int currentIndex = head + i;
                if (obj.equals(data[(currentIndex) % data.length])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Возвращает итератор по элементам дека.
     * @return итератор
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = head;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < currentSize;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E e = data[currentIndex];
                currentIndex = (currentIndex + 1) % data.length;
                count++;
                return e;
            }
        };
    }

    /**
     * Возвращает массив, содержащий все элементы дека.
     * @return массив элементов
     */
    @Override
    public Object[] toArray() {
        Object[] obj = new Object[currentSize];
        int index = head;
        for (int i = 0; i < currentSize; i++) {
            obj[i] = data[index];
            index = (index + 1) % data.length;
        }
        return obj;
    }

    /**
     * Возвращает массив, содержащий все элементы дека.
     * @param arr массив, в который будут помещены элементы (если он достаточно большой)
     * @return массив элементов
     */
    @Override
    public <T> T[] toArray(T[] arr) {
        if (arr.length < currentSize) {
            arr = (T[]) Array.newInstance(arr.getClass().getComponentType(), currentSize);
        }
        int index = head;
        for (int i = 0; i < currentSize; i++) {
            arr[i] = (T) data[index];
            index = (index + 1) % data.length;
        }
        if (arr.length > currentSize) {
            arr[currentSize] = null;
        }
        return arr;
    }

    /**
     * Добавляет элемент в конец дека.
     * @param e элемент для добавления
     * @return true (как указано в интерфейсе Collection)
     */
    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    /**
     * Удаляет первое вхождение указанного элемента.
     * @param o элемент для удаления
     * @return true если элемент был найден и удален
     */
    @Override
    public boolean remove(Object o) {
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (o.equals(iterator.next())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет, содержатся ли все элементы коллекции в деке.
     * @param c коллекция для проверки
     * @return true если все элементы коллекции содержатся в деке
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
     * Добавляет все элементы коллекции в дек.
     * @param c коллекция для добавления
     * @return true если дек изменился
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return !c.isEmpty();
    }

    /**
     * Удаляет из дека все элементы, содержащиеся в указанной коллекции.
     * @param c коллекция элементов для удаления
     * @return true если дек изменился
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isCorrect = false;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (c.contains(iterator.next())) {
                iterator.remove();
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    /**
     * Сохраняет в деке только элементы, содержащиеся в указанной коллекции.
     * @param c коллекция элементов для сохранения
     * @return true если дек изменился
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isCorrect = false;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    /**
     * Удаляет все элементы из дека.
     */
    @Override
    public void clear() {
        for (int i = 0; i < data.length; i++) {
            data[i] = null;
        }
        currentSize = head = tail = 0;
    }

    /**
     * Добавляет элемент в начало дека.
     * @param e элемент для добавления
     */
    public void addFirst(E e) {
        if (currentSize == data.length) {
            resize();
        }
        head = (head - 1 + data.length) % data.length;
        data[head] = e;
        currentSize++;
    }

    /**
     * Добавляет элемент в конец дека.
     * @param e элемент для добавления
     */
    public void addLast(E e) {
        if (currentSize == data.length) {
            resize();
        }
        data[tail] = e;
        tail = (tail + 1) % data.length;
        currentSize++;
    }

    /**
     * Возвращает первый элемент дека без удаления.
     * @return первый элемент
     * @throws NoSuchElementException если дек пуст
     */
    public E element() {
        if (currentSize == 0) {
            throw new NoSuchElementException();
        }
        return data[head];
    }

    /**
     * Возвращает первый элемент дека без удаления.
     * @return первый элемент
     * @throws NoSuchElementException если дек пуст
     */
    public E getFirst() {
        if (currentSize == 0) {
            throw new NoSuchElementException();
        }
        return data[head];
    }

    /**
     * Возвращает последний элемент дека без удаления.
     * @return последний элемент
     * @throws NoSuchElementException если дек пуст
     */
    public E getLast() {
        if (currentSize == 0) {
            throw new NoSuchElementException();
        }
        return data[(tail - 1 + data.length) % data.length];
    }

    /**
     * Извлекает и удаляет первый элемент дека.
     * @return первый элемент или null если дек пуст
     */
    public E poll() {
        return pollFirst();
    }

    /**
     * Извлекает и удаляет первый элемент дека.
     * @return первый элемент или null если дек пуст
     */
    public E pollFirst() {
        if (currentSize == 0) {
            return null;
        }
        E e = data[head];
        data[head] = null;
        head = (head + 1) % data.length;
        currentSize--;
        return e;
    }

    /**
     * Извлекает и удаляет последний элемент дека.
     * @return последний элемент или null если дек пуст
     */
    public E pollLast() {
        if (currentSize == 0) {
            return null;
        }
        tail = (tail - 1 + data.length) % data.length;
        E e = data[tail];
        data[tail] = null;
        currentSize--;
        return e;
    }

    /**
     * Увеличивает емкость внутреннего массива.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        E[] newArray = (E[]) new Object[data.length * 2];
        int index = head;
        for (int i = 0; i < currentSize; i++) {
            newArray[i] = data[index];
            index = (index + 1) % data.length;
        }
        data = newArray;
        head = 0;
        tail = currentSize;
    }


    public static void main(String[] args) {


        MyArrayDeque<String> deque = new MyArrayDeque<>();
        System.out.println("Создан новый дек: " + deque);
        System.out.println("Размер пустого дека: " + deque.size());
        System.out.println("Дек пуст? " + deque.isEmpty());

        // Тестируем добавление элементов
        System.out.println("\n=== Тест добавления элементов ===");
        deque.add("Первый");
        deque.addLast("Второй");
        deque.addFirst("Новый первый");
        System.out.println("Дек после добавления элементов: " + deque);
        System.out.println("Размер дека: " + deque.size());

        // Тестируем получение элементов
        System.out.println("\n=== Тест получения элементов ===");
        System.out.println("Первый элемент: " + deque.getFirst());
        System.out.println("Последний элемент: " + deque.getLast());

        // Тестируем удаление элементов
        System.out.println("\n=== Тест удаления элементов ===");
        System.out.println("Удален первый элемент: " + deque.pollFirst());
        System.out.println("Удален последний элемент: " + deque.pollLast());
        System.out.println("Дек после удаления: " + deque);

        // Тестируем работу с коллекциями
        System.out.println("\n=== Тест работы с коллекциями ===");
        MyArrayDeque<String> temp = new MyArrayDeque<>();
        temp.add("Третий");
        temp.add("Четвертый");

        deque.addAll(temp);
        System.out.println("Дек после addAll: " + deque);
        System.out.println("Содержит все элементы temp? " + deque.containsAll(temp));

        // Тестируем итератор
        System.out.println("\n=== Тест итератора ===");
        System.out.println("Элементы через итератор:");
        for (String s : deque) {
            System.out.println(s);
        }

        System.out.println("\n=== Тест очистки дека ===");
        deque.clear();
        System.out.println("Дек после очистки: " + deque);
        System.out.println("Размер: " + deque.size());
        System.out.println("Пуст? " + deque.isEmpty());

        // Тестируем обработку ошибок
        System.out.println("\n=== Тест обработки ошибок ===");
        try {
            System.out.println("Попытка получить первый элемент пустого дека:");
            deque.getFirst();
        } catch (NoSuchElementException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

    }
}
