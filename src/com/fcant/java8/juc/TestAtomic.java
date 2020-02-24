package com.fcant.java8.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TestAtmoic
 * <p>
 * encoding:UTF-8
 *
 * 一、i++的原子性问题：i++的操作实际上分为三个步骤“读-改-写”
 *      int i = 10;
 *      i = i++; // 10
 *      int temp = i;
 *      i = i + 1;
 *      i = temp;
 *
 * 二、原子变量：JDK1.5后，java.util.concurrent.atomic 包下提供了常用的原子变量
 *      1.Volatile保证内存可见性
 *      2.CAS（Compare-And-Swap）算法保证数据的原子性
 *          内存值 V
 *          预估值 A
 *          更新值 B
 *          当且仅当V == A时，V = B，否则，将不作任何操作
 *
 * @author Fcant 下午 17:34:21 2020/2/24/0024
 */
public class TestAtomic {

    public static void main(String[] args) {
        AtomicFh atomicFh = new AtomicFh();
        for (int i = 0; i < 10; i++) {
            new Thread(atomicFh).start();
        }
    }
}

class AtomicFh implements Runnable {

    // private volatile int serialNumber = 0;
    private AtomicInteger serialNumber = new AtomicInteger();

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " : " + getSerialNumber());
    }
}
