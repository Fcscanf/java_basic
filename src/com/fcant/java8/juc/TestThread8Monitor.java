package com.fcant.java8.juc;

/**
 * TestThread8Monitor
 * <p>
 * encoding:UTF-8
 *
 * 题目：判断打印的one或two
 * 1.两个普通同步方法，两个线程，标准打印 // one two
 * 2.新增Thread.sleep() 给 getOne()方法  // one two
 * 3.新增普通方法getThree() // three one two
 * 4.两个普通同步方法，两个Number对象  // two one
 * 5.修改getOne()为静态同步方法  // one two
 * 6.修改两个方法均为静态同步方法，一个Number对象 // one two
 * 7.一个静态同步方法，一个非静态同步方法，两个Number对象  // two one
 * 8.两个静态同步方法，两个Number对象  // one two
 *
 * 线程八锁的关键：
 * ①非静态方法的锁默认为this，静态方法的锁为对应的Class实例
 * ②某一时刻内，只能有一个线程持有锁，无论几个方法
 *
 * @author Fcant 下午 15:11:22 2020/2/25/0025
 */
public class TestThread8Monitor {
    public static void main(String[] args) {
        Number number = new Number();
        Number num = new Number();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                num.getTwo();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getThree();
            }
        }).start();
    }
}

class Number {
    public synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ONE");
    }

    public synchronized void getTwo() {
        System.out.println("TWO");
    }

    public synchronized void getThree() {
        System.out.println("Three");
    }
}
