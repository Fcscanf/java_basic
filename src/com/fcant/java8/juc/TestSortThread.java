package com.fcant.java8.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TestSortThread
 * <p>
 * encoding:UTF-8
 *
 * 编写一个程序，开启3个线程，这三个线程的ID分别为A、B、C，每个线程将自己的ID在屏幕上打印10遍，要求输出的结果必须按顺序显示
 * 如：ABCABCABC···依次递归
 *
 * @author Fcant 下午 13:52:37 2020/2/25/0025
 */
public class TestSortThread {
    public static void main(String[] args) {
        Alternate alternate = new Alternate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 20; i++) {
                    alternate.loopA(i);
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 20; i++) {
                    alternate.loopB(i);
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 20; i++) {
                    alternate.loopC(i);
                }
                System.out.println("------------------------");
            }
        }, "C").start();
    }
}

class Alternate {
    private int num = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(int totalLoop) {
        lock.lock();
        try {
            if (num != 1) {
                condition1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "-" + i + "-" + totalLoop);
            }
            num = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop) {
        lock.lock();
        try {
            if (num != 2) {
                condition2.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "-" + i + "-" + totalLoop);
            }
            num = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop) {
        lock.lock();
        try {
            if (num != 3) {
                condition3.await();
            }
            for (int i = 0; i < 20; i++) {
                System.out.println(Thread.currentThread().getName() + "-" + i + "-" + totalLoop);
            }
            num = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
