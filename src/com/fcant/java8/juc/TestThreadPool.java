package com.fcant.java8.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * TestThreadPool
 * <p>
 * encoding:UTF-8
 *
 * 一、线程池
 * 提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应速率
 *
 * 二、线程池体系结构
 * java.util.concurrent.Ececutor: 负责线程的使用与调度的根接口
 * |--ExecutorService 子接口：线程池的主要接口
 *      |--ThreadPoolExecutor 线程池的实现类
 *      |--ScheduledExecutorService 子接口：负责线程的调度
 *           |--ScheduledThreadPoolExecutor：继承ThreadPoolExecutor，实现ScheduledExecutorService
 *
 * 三、工具类：Executors
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池。
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池，线程池中只有一个线程。
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 *
 * @author Fcant 下午 16:03:54 2020/2/25/0025
 */
public class TestThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolFc fc = new ThreadPoolFc();
        // 1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        // 2.为线程池分配任务
        /*for (int i = 0; i < 10; i++) {
            executorService.submit(fc);
        }*/
        List<Future<Integer>> list = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int i = 0; i < 100; i++) {
                        sum += i;
                    }
                    return sum;
                }
            });
            list.add(future);
        }

        // 3.关闭线程池
        executorService.shutdown();

        for (Future<Integer> integerFuture : list) {
            System.out.println(integerFuture.get());
        }
        
//        new Thread(fc).start();
//        new Thread(fc).start();
    }
}

class ThreadPoolFc implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
        }
    }
}