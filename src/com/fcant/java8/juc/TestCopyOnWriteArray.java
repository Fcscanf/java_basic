package com.fcant.java8.juc;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TestCopyOnWriteArray
 * <p>
 * encoding:UTF-8
 *
 * CopyOnWriteArrayList/CopyOnWriteArraySet：“写入并复制”
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制。并发迭代操作时可以选择
 *
 * @author Fcant 下午 18:47:27 2020/2/24/0024
 */
public class TestCopyOnWriteArray {

    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();
        for (int i = 0; i < 10; i++) {
            new Thread(helloThread).start();
        }
    }
}

class HelloThread implements Runnable {

    // private static List<String> list = Collections.synchronizedList(new ArrayList<>());
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            list.add("DD");
        }
    }
}
