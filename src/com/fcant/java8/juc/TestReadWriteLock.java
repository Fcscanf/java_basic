package com.fcant.java8.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * TestReadWriteLock
 * <p>
 * encoding:UTF-8
 *
 * 1.ReadWriteLock：读写锁
 * 写写/读写 需要“互斥”
 * 读读 不需要互斥
 *
 * @author Fcant 下午 14:53:31 2020/2/25/0025
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        ReadWriteLockFh readWriteLockFh = new ReadWriteLockFh();
        new Thread(new Runnable() {
            @Override
            public void run() {
                readWriteLockFh.set((int) (Math.random() * 101));
            }
        }, "Write").start();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockFh.get();
                }
            }, "Read").start();
        }
    }
}

class ReadWriteLockFh {
    private int num = 0;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 读
    public void get() {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " : " + num);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    // 写
    public void set(int num) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
            this.num = num;
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
}