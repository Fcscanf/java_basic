package com.fcant.java8.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TestLock
 * <p>
 * encoding:UTF-8
 *
 * 一、用于解决多线程安全问题的方式
 * synchronized：隐式锁
 * 1.同步代码块
 * 2.同步方法
 *
 * jdk1.5以后：
 * 3.同步锁Lock
 * 注意：同步锁是一个显示锁，需要通过lock()上锁，必须通过unlock()方法进行释放锁
 *
 * @author Fcant 上午 10:02:19 2020/2/25/0025
 */
public class TestLock {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }
}

class Ticket implements Runnable {

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "完成售票，余票为：" + --tick);
                }
            }finally {
                lock.unlock();
            }

        }
    }
}
