package com.fcant.java8.juc;

/**
 * TestCompareAndSwap
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 18:09:34 2020/2/24/0024
 */
public class TestCompareAndSwap {

    public static void main(String[] args) {

        final CompareAndSwap cas = new CompareAndSwap();
        for(int i=1 ;i < 10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.getValue();
                    boolean compareAndSet = cas.compareAndSet(expectedValue, (int) Math.random() * 101);
                    System.out.println(compareAndSet);
                }
            });
        }
    }
}

class CompareAndSwap {
    private int value;

    // 获取内存值
    public synchronized int getValue() {
        return value;
    }

    // 比较
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            oldValue = newValue;
        }
        return oldValue;
    }

    // 设置
    public synchronized boolean compareAndSet(int ecpectedValue, int newValue) {
        return ecpectedValue == compareAndSwap(ecpectedValue, newValue);
    }
}
