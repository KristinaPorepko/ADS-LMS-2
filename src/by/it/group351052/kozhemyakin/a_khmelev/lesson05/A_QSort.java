package by.it.group351052.kozhemyakin.a_khmelev.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Видеорегистраторы и площадь.
На площади установлена одна или несколько камер.
Известны данные о том, когда каждая из них включалась и выключалась (отрезки работы)
Известен список событий на площади (время начала каждого события).
Вам необходимо определить для каждого события сколько камер его записали.

В первой строке задано два целых числа:
    число включений камер (отрезки) 1<=n<=50000
    число событий (точки) 1<=m<=50000.

Следующие n строк содержат по два целых числа ai и bi (ai<=bi) -
координаты концов отрезков (время работы одной какой-то камеры).
Последняя строка содержит m целых чисел - координаты точек.
Все координаты не превышают 10E8 по модулю (!).

Точка считается принадлежащей отрезку, если она находится внутри него или на границе.

Для каждой точки в порядке их появления во вводе выведите,
скольким отрезкам она принадлежит.
    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/

public class A_QSort {

    //отрезок
    private class Segment  implements Comparable<Segment>{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
            //тут вообще-то лучше доделать конструктор на случай если
            //концы отрезков придут в обратном порядке
        }

        @Override
        public int compareTo(Segment o) {
            return 0;
        }
    }

    int[] getAccessory(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        //читаем отрезки
        int[] starts = new int[n];
        int[] ends   = new int[n];
        for (int i = 0; i < n; i++) {
            int s = scanner.nextInt();
            int t = scanner.nextInt();
            // ai <= bi по условию, значит s <= t
            segments[i] = new Segment(s, t);
            starts[i] = s;
            ends[i] = t;
        }

        //читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Отсортируем массивы starts и ends быстрыми сортировками
        quickSort(starts, 0, n - 1);
        quickSort(ends,   0, n - 1);

        // Для каждой точки считаем,
        // сколько отрезков ей покрывается:
        //  c1 = количество starts, которые <= point
        //  c2 = количество ends,   которые <  point
        //  answer = c1 - c2
        //
        // Если точка на границе (point == end), то она входит в отрезок.

        for (int i = 0; i < m; i++) {
            int point = points[i];
            // c1 - сколько starts <= point
            int c1 = countLessOrEqual(starts, point);
            // c2 - сколько ends < point
            int c2 = countLess(ends, point);
            result[i] = c1 - c2;
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    // Быстрая сортировка массива (реализация in-place)
    private void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, left, right);
            quickSort(arr, left, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, right);
        }
    }

    // Разбиение массива для быстрой сортировки
    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // Считаем, сколько элементов в arr строго меньше value
    private int countLess(int[] arr, int value) {
        // бинарный поиск
        int left = 0;
        int right = arr.length - 1;
        int res = arr.length;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (arr[mid] < value) {
                left = mid + 1;
            } else {
                res = mid;
                right = mid - 1;
            }
        }
        return res;
    }

    // Считаем, сколько элементов в arr <= value
    private int countLessOrEqual(int[] arr, int value) {
        // бинарный поиск
        int left = 0;
        int right = arr.length - 1;
        int res = arr.length;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (arr[mid] <= value) {
                left = mid + 1;
            } else {
                res = mid;
                right = mid - 1;
            }
        }
        return res;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataA.txt");
        A_QSort instance = new A_QSort();
        int[] result = instance.getAccessory(stream);
        for (int index: result){
            System.out.print(index + " ");
        }
    }
}
