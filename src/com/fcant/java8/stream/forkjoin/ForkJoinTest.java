package com.fcant.java8.stream.forkjoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * ForkJoinTest
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 21:58:27 2020/2/22/0022
 */
public class ForkJoinTest {

    // Fork/Join操作
    @Test
    public void forkJoinTest() {
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinCalculate task = new ForkJoinCalculate(0L, 10000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis() + "毫秒");
    }

    // 常规行程操作
    @Test
    public void threadTest() {
        Instant start = Instant.now();
        Long sum = 0L;
        for(long i=0 ;i < 10000000L;i++){
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis() + "毫秒");
    }

    // Java8并行流的操作
    @Test
    public void parallelTest() {
        Instant start = Instant.now();
        LongStream.rangeClosed(0, 100000000L)
                .parallel()
                .reduce(0, Long::sum);
        Instant end = Instant.now();
        System.out.println("耗时为：" + Duration.between(start, end).toMillis() + "毫秒");
    }
}
