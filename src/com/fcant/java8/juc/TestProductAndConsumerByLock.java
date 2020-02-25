package com.fcant.java8.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TestProductorAndConsumer
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 上午 10:46:52 2020/2/25/0025
 */
public class TestProductAndConsumerByLock {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Product product = new Product(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(product, "生产者A").start();
        new Thread(consumer, "消费者B").start();

        new Thread(product, "生产者C").start();
        new Thread(consumer, "消费者D").start();
    }
}

// 店员
class Clerk{
    private int product = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    // 进货
    public void get() {
        lock.lock();
        try {
            while (product >= 1) {
                System.out.println("仓库已满，无法进货！");
                condition.signal();
            }
            System.out.println(Thread.currentThread().getName() + " : " + ++product);
            this.notifyAll();
        }finally {
            condition.signalAll();
        }
    }

    // 卖货
    public synchronized void sale() {
        lock.lock();
        try {
            while (product <= 0) {
                condition.signal();
                System.out.println("缺货中···");
            }
            System.out.println(Thread.currentThread().getName() + " : " + --product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }
}

// 生产者
class Product implements Runnable{
    private Clerk clerk;

    public Product(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

// 消费者
class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}