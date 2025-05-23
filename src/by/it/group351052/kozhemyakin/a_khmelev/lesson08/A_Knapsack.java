package by.it.group351052.kozhemyakin.a_khmelev.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: рюкзак с повторами

Первая строка входа содержит целые числа
    1<=W<=100000     вместимость рюкзака
    1<=n<=300        сколько есть вариантов золотых слитков
                     (каждый можно использовать множество раз).
Следующая строка содержит n целых чисел, задающих веса слитков:
  0<=w[1]<=100000 ,..., 0<=w[n]<=100000

Найдите методами динамического программирования
максимальный вес золота, который можно унести в рюкзаке.

Sample Input:
10 3
1 4 8
Sample Output:
10

Sample Input 2:
15 3
2 8 16
Sample Output 2:
14
*/

public class A_Knapsack {

    int getMaxWeight(InputStream stream ) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt();     // вместимость рюкзака
        int n = scanner.nextInt();     // количество видов слитков
        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // dp[i] будет хранить максимально возможный вес, который можно набрать,
        // не превосходящий i, используя неограниченное количество слитков.
        int[] dp = new int[W + 1];

        // Инициализация
        // dp[0] = 0 по умолчанию, т.к. при вместимости 0 можно взять только 0.

        // Перебираем вместимости от 1 до W
        for (int i = 1; i <= W; i++) {
            for (int j = 0; j < n; j++) {
                int weight = gold[j];
                if (weight <= i) {
                    int candidate = dp[i - weight] + weight;
                    if (candidate > dp[i]) {
                        dp[i] = candidate;
                    }
                }
            }
        }

        // dp[W] - это максимально возможный вес для вместимости W
        int result = dp[W];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
