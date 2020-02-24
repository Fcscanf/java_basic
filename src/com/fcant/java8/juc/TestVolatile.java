package com.fcant.java8.juc;

/**
 * TestVolatile
 * <p>
 * encoding:UTF-8
 *
 * volatile关键字：当多个线程操作数据时，可以保证内存中的数据的可见性
 *                相较于synchronize是一种较为轻量级的同步策略
 *  注意：
 *  1.volatile 不具备“互斥性”
 *  2.volatile 不能保证变量的“原子性”
 *
 * @author Fcant 下午 12:49:36 2020/2/24/0024
 */
public class TestVolatile {
    public static void main(String[] args) {
        ThreadFh fh = new ThreadFh();
        new Thread(fh).start();

        while (true) {
            if (fh.isFlag()) {
                System.out.println("-----------------");
                break;
            }
        }
    }
}

class ThreadFh implements Runnable {
    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }
}
