package com.fcant.java8.juc;

import java.util.concurrent.CountDownLatch;

/**
 * TestCountDownLatch
 * <p>
 * encoding:UTF-8
 *
 * CountDownLatch：闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 *
 * @author Fcant 下午 19:24:36 2020/2/24/0024
 */
public class TestCountDownLatch {
    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        LatchFh fh = new LatchFh(countDownLatch);
        long start = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(fh).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("耗时为：" + (end - start));
    }
}

class LatchFh implements Runnable {

    private CountDownLatch countDownLatch;

    public LatchFh(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 50000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            }finally {
                countDownLatch.countDown();
            }
        }

    }
}
