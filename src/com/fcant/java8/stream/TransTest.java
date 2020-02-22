package com.fcant.java8.stream;

import com.fcant.java8.stream.bean.Trader;
import com.fcant.java8.stream.bean.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * TransTest
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 17:53:18 2020/2/22/0022
 */
public class TransTest {

    List<Transaction> transactions = null;

    @Before
    public void before() {
        Trader roal = new Trader("Roal", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2019, 300),
                new Transaction(roal, 2013, 1000),
                new Transaction(roal, 2015, 400),
                new Transaction(mario, 2013, 710),
                new Transaction(mario, 2013, 700),
                new Transaction(alan, 2014, 950)
        );
    }

    // 1.找出2013年发生的所有交易，并按交易额排序（从低到高）
    @Test
    public void sortTest() {
        transactions.stream()
                .filter(t -> t.getYear() == 2013)
                .sorted((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()))
                .forEach(System.out::println);
    }

    // 2.交易员都在哪些不同的城市工作过
    @Test
    public void cityTest() {
        transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
    }

    // 3.查找所有来自剑桥的交易员，并按姓名排序
    @Test
    public void nameSortTest() {
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .distinct()
                .sorted((t1, t2) -> t1.getName().compareTo(t2.getCity()))
                .forEach(System.out::println);
    }

    // 4.返回所有交易员的姓名字符串，按字母排序
    @Test
    public void nameStrTest() {
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted(String::compareTo)
                .forEach(System.out::println);

        System.out.println("--------------------------");

        transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .forEach(System.out::println);

        String traderName = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", String::concat);
        System.out.println(traderName);
    }

    // 5.有没有交易员是在米兰工作的
    @Test
    public void baseTest() {
        boolean baseAddress = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(baseAddress);
    }

    // 6.打印生活在剑桥的交易员的所有交易额
    @Test
    public void lifeTest() {
        Optional<Integer> sum = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::sum);
        System.out.println(sum.get());
    }

    // 7.所有交易中，最高的交易额是多少
    @Test
    public void maxTest() {
        Optional<Integer> max = transactions.stream()
                .map(transaction -> transaction.getValue())
                .max(Integer::compare);
        System.out.println(max.get());
    }

    // 8.找到交易额最小的交易
    @Test
    public void minTest() {
        Optional<Transaction> min = transactions.stream()
                .min((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()));
        System.out.println(min);
    }

}
