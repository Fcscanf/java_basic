package com.fcant.java8.juc;

/**
 * TestVolatile
 * <p>
 * encoding:UTF-8
 *
 * volatile关键字
 *
 * @author Fcant 下午 12:49:36 2020/2/24/0024
 */
public class TestVolatile {
    public static void main(String[] args) {
        ThreadFh fh = new ThreadFh();
        new Thread(fh).start();

        while (true) {
            synchronized (fh) {
                if (fh.isFlag()) {
                    System.out.println("-----------------");
                    break;
                }
            }
        }
    }
}

class ThreadFh implements Runnable {
    private boolean flag = false;

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
