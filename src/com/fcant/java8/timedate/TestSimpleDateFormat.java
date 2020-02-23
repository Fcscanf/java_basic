package com.fcant.java8.timedate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * TestSimpleDateFormat
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 17:53:11 2020/2/23/0023
 */
public class TestSimpleDateFormat {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-mm-dd");
        Callable<Date> task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("2020-02-23");
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<Date>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(pool.submit(task));
        }
        for (Future<Date> future : futures) {
            System.out.println(future.get());
        }
        pool.shutdown();
    }
}
