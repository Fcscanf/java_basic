package com.fcant.java8.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * TestCallable
 * <p>
 * encoding:UTF-8
 *
 * 一、创建执行线程的方式三：实现Callable接口。相较于实现Runnable接口的方式，Callable方法可以有返回值，并且可以抛出异常
 * 二、执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果。FutureTask是Future接口的实现类
 *
 * @author Fcant 下午 21:17:28 2020/2/24/0024
 */
public class TestCallable {
    public static void main(String[] args) {
        ThreadFc fc = new ThreadFc();
        // 1.执行Callable方式，需要FutureTask实现类的支持，用于接收运算结果
        FutureTask<Integer> futureTask = new FutureTask<>(fc);
        new Thread(futureTask).start();

        // 2.接收线程运算后的结果
        try {
            // FutureTask可用于闭锁
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class ThreadFc implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}
