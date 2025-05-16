package by.it.group310951.porepko.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Реализация интерфейса List с использованием динамического массива.
public class ListC<E> implements List<E> {

    // Начальная емкость массива по умолчанию
    private static final int CAPACITY = 10;

    // Массив для хранения элементов списка
    private Object[] data;

    // Текущее количество элементов в списке
    private int currentSize;

    // Конструктор по умолчанию. Инициализирует массив начальной емкостью.

    public ListC() {
        data = new Object[CAPACITY];
        currentSize = 0;
    }

    /////////////////////////////////////////////////////////////////////////
    ////////////////// Обязательные к реализации методы //////////////////////
    /////////////////////////////////////////////////////////////////////////

    // Возвращает строковое представление списка.

    @Override
    public String toString() {
        StringBuilder resultStr = new StringBuilder("[");
        for (int i = 0; i < currentSize; i++) {
            resultStr.append(data[i]);
            if (i < currentSize - 1) {
                resultStr.append(", ");
            }
        }
        resultStr.append("]");
        return resultStr.toString();
    }

    /**
     * Добавляет элемент в конец списка.
     * @param e элемент для добавления
     * @return true (как указано в интерфейсе Collection)
     */
    @Override
    public boolean add(E e) {
        if (currentSize == data.length) {
            resize(null);
        }
        data[currentSize++] = e;
        return true;
    }

    /**
     * Удаляет элемент по указанному индексу.
     * @param index индекс удаляемого элемента
     * @return удаленный элемент
     * @throws IndexOutOfBoundsException если индекс выходит за границы списка
     */
    @Override
    public E remove(int index) {
        isCorrectIndex(index);
        E delElm = (E) data[index];
        System.arraycopy(data, index + 1, data, index, currentSize - index - 1);
        currentSize--;
        data[currentSize] = null;
        return delElm;
    }

    // Возвращает количество элементов в списке.
    @Override
    public int size() {
        return currentSize;
    }

    /**
     * Вставляет элемент в указанную позицию.
     * @param index индекс, по которому вставляется элемент
     * @param element элемент для вставки
     * @throws IndexOutOfBoundsException если индекс выходит за границы списка
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > currentSize) {
            throw new IndexOutOfBoundsException();
        }
        if (currentSize == data.length) {
            resize(null);
        }
        System.arraycopy(data, index, data, index + 1, currentSize - index);
        data[index] = element;
        currentSize++;
    }

    /**
     * Удаляет первое вхождение указанного элемента.
     * @param o элемент для удаления
     * @return true если элемент был найден и удален
     */
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * Заменяет элемент в указанной позиции.
     * @param index индекс заменяемого элемента
     * @param element новый элемент
     * @return предыдущий элемент
     * @throws IndexOutOfBoundsException если индекс выходит за границы списка
     */
    @Override
    public E set(int index, E element) {
        isCorrectIndex(index);
        E oldElement = (E) data[index];
        data[index] = element;
        return oldElement;
    }

    /**
     * Проверяет, пуст ли список.
     * @return true если список не содержит элементов
     */
    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Удаляет все элементы из списка.
     */
    @Override
    public void clear() {
        for (int i = 0; i < currentSize; i++) {
            data[i] = null;
        }
        currentSize = 0;
    }

    /**
     * Возвращает индекс первого вхождения указанного элемента.
     * @param o искомый элемент
     * @return индекс элемента или -1 если элемент не найден
     */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < currentSize; i++) {
            if (data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Возвращает элемент по указанному индексу.
     * @param index индекс элемента
     * @return элемент в указанной позиции
     * @throws IndexOutOfBoundsException если индекс выходит за границы списка
     */
    @Override
    public E get(int index) {
        isCorrectIndex(index);
        return (E) data[index];
    }

    /**
     * Проверяет, содержится ли элемент в списке.
     * @param o искомый элемент
     * @return true если элемент найден
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Возвращает индекс последнего вхождения указанного элемента.
     * @param o искомый элемент
     * @return индекс элемента или -1 если элемент не найден
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = currentSize - 1; i >= 0; i--) {
            if (data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Проверяет, содержатся ли все элементы коллекции в списке.
     * @param c коллекция для проверки
     * @return true если все элементы коллекции содержатся в списке
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
     * Добавляет все элементы коллекции в конец списка.
     * @param c коллекция для добавления
     * @return true если список изменился
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            modified |= add(e);
        }
        return modified;
    }

    /**
     * Вставляет все элементы коллекции в указанную позицию.
     * @param index позиция для вставки
     * @param c коллекция для добавления
     * @return true если список изменился
     * @throws IndexOutOfBoundsException если индекс выходит за границы списка
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > currentSize) {
            throw new IndexOutOfBoundsException();
        }
        if (c.isEmpty()) {
            return false;
        }
        resize(currentSize + c.size());
        int numNew = c.size();
        System.arraycopy(data, index, data, index + numNew, currentSize - index);
        int i = index;
        for (E e : c) {
            data[i++] = e;
        }
        currentSize += numNew;
        return true;
    }

    /**
     * Удаляет все элементы, содержащиеся в указанной коллекции.
     * @param c коллекция элементов для удаления
     * @return true если список изменился
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Сохраняет только элементы, содержащиеся в указанной коллекции.
     * @param c коллекция элементов для сохранения
     * @return true если список изменился
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    /////////////////////////////////////////////////////////////////////////
    ////////////////// Опциональные к реализации методы /////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return java.util.Arrays.copyOf(data, currentSize);
    }

    /////////////////////////////////////////////////////////////////////////
    //////////////////////// Вспомогательные методы /////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < currentSize;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException();
                }
                return (E) data[currentIndex++];
            }

            @Override
            public void remove() {
                if (currentIndex <= 0 || currentIndex > currentSize) {
                    throw new IllegalStateException();
                }
                ListC.this.remove(--currentIndex);
            }
        };
    }

    /**
     * Изменяет размер внутреннего массива.
     * @param Capacity новая емкость (если null, удваивает текущую емкость)
     */
    private void resize(Integer Capacity) {
        if (Capacity == null) {
            Capacity = data.length * 2;
        }
        if (Capacity > data.length) {
            data = java.util.Arrays.copyOf(data, Capacity);
        }
    }

    /**
     * Проверяет корректность индекса.
     * @param currentIndex проверяемый индекс
     * @throws IndexOutOfBoundsException если индекс некорректен
     */
    private void isCorrectIndex(int currentIndex) {
        if (currentIndex < 0 || currentIndex >= currentSize) {
            throw new IndexOutOfBoundsException("Index: " + currentIndex + ", Size: " + currentSize);
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //////////////////////////// Main-метод /////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        System.out.println("=== Тестирование реализации списка ListC ===");

        // Создаем список
        ListC<String> list = new ListC<>();
        System.out.println("Создан новый список: " + list);
        System.out.println("Размер пустого списка: " + list.size());
        System.out.println("Список пуст? " + list.isEmpty());

        // Тестируем добавление элементов
        System.out.println("\n=== Тест добавления элементов ===");
        list.add("Первый");
        list.add("Второй");
        list.add("Третий");
        System.out.println("Список после добавления 3 элементов: " + list);
        System.out.println("Размер списка: " + list.size());
        System.out.println("Список пуст? " + list.isEmpty());

        // Тестируем добавление по индексу
        list.add(1, "Новый второй");
        System.out.println("\nПосле добавления по индексу 1: " + list);

        // Тестируем получение элементов
        System.out.println("\n=== Тест получения элементов ===");
        System.out.println("Элемент с индексом 0: " + list.get(0));
        System.out.println("Элемент с индексом 2: " + list.get(2));

        // Тестируем замену элементов
        String old = list.set(2, "Замененный");
        System.out.println("\nЗаменен элемент " + old + " на 'Замененный'");
        System.out.println("Список после замены: " + list);

        // Тестируем удаление
        System.out.println("\n=== Тест удаления элементов ===");
        System.out.println("Удален элемент с индексом 1: " + list.remove(1));
        System.out.println("Список после удаления: " + list);

        System.out.println("Удален элемент 'Третий': " + list.remove("Третий"));
        System.out.println("Список после удаления: " + list);

        // Тестируем поиск
        System.out.println("\n=== Тест поиска элементов ===");
        System.out.println("Индекс 'Первый': " + list.indexOf("Первый"));
        System.out.println("Содержит 'Второй'? " + list.contains("Второй"));
        System.out.println("Содержит 'Несуществующий'? " + list.contains("Несуществующий"));

        // Тестируем очистку
        System.out.println("\n=== Тест очистки списка ===");
        list.clear();
        System.out.println("Список после очистки: " + list);
        System.out.println("Размер: " + list.size());
        System.out.println("Пуст? " + list.isEmpty());

        // Тестируем работу с коллекциями
        System.out.println("\n=== Тест работы с коллекциями ===");
        ListC<String> fruits = new ListC<>();
        fruits.add("Яблоко");
        fruits.add("Банан");
        fruits.add("Апельсин");

        list.addAll(fruits);
        System.out.println("После addAll: " + list);

        ListC<String> toRemove = new ListC<>();
        toRemove.add("Банан");
        list.removeAll(toRemove);
        System.out.println("После removeAll('Банан'): " + list);

        ListC<String> toRetain = new ListC<>();
        toRetain.add("Апельсин");
        list.retainAll(toRetain);
        System.out.println("После retainAll('Апельсин'): " + list);

        // Тестируем итератор
        System.out.println("\n=== Тест итератора ===");
        list.clear();
        list.add("Один");
        list.add("Два");
        list.add("Три");

        System.out.println("Элементы через итератор:");
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // Тестируем обработку ошибок
        System.out.println("\n=== Тест обработки ошибок ===");
        try {
            System.out.println("Попытка получить элемент с несуществующим индексом:");
            list.get(10);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        try {
            System.out.println("Попытка добавить элемент по несуществующему индексу:");
            list.add(5, "Ошибка");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("\n=== Тестирование завершено ===");
    }
}

