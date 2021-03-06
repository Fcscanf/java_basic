package com.fcant.nio.buffer;

import java.nio.ByteBuffer;

/**
 * AboutBuffer
 * <p>
 * encoding:UTF-8
 * 1、缓冲区（Buffer）：在Java NIO中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *
 * 上述缓冲区的管理方式几乎一致，通过allocate()获取缓冲区
 *
 * 2、缓冲区存取数据的两个核心方法
 * put():存入数据到缓冲区中
 * get():获取缓冲区中的数据
 *
 * 3、缓冲区的四个核心属性
 * capacity：容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变
 * limit：界限，表示缓冲区中可以操作数据的大小。（limit后数据不能进行读写）
 * position：位置，表示缓冲区正在操作数据的位置
 * mark:标记，表示当前position的位置。可以通过reset() 恢复到mark的位置
 *
 * 0 <= mark <= position <= limit <= capacity
 *
 * 4、直接缓冲区与非直接缓冲区
 * 非直接缓冲区：通过allocate()方法分配缓冲区，将缓冲区建立在JVM的内存中
 * 直接缓冲区：通过allocateDirect()方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 *
 * @author Fcant 下午 20:43:08 2020/2/10/0010
 */
public class AboutBuffer {

    public static void directBuffer() {
        // 分配直接缓冲区
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(1024);
        // 判断是否为直接缓冲区
        System.out.println(allocateDirect.isDirect());
    }

    public static void bufferMark() {
        String str = "abcde";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes, 0, 2);
        System.out.println(new String(bytes, 0, 2));
        System.out.println(byteBuffer.position());

        // mark():标记
        byteBuffer.mark();
        byteBuffer.get(bytes, 2, 2);
        System.out.println(new String(bytes, 2, 2));
        System.out.println(byteBuffer.position());

        // reset():恢复到mark的位置
        byteBuffer.reset();
        System.out.println(byteBuffer.position());

        // 判断缓冲区中是否还有剩余数据
        if (byteBuffer.hasRemaining()) {
            // 获取缓冲区中可以操作的数量
            System.out.println(byteBuffer.remaining());
        }
    }

    public static void aboutByteBuffer() {

        String abc = "abcde";
        // 1.分配一个指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        System.out.println("------------allocate()----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 2.利用put() 存入数据到缓冲区中
        byteBuffer.put(abc.getBytes());
        System.out.println("------------put()----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 3.切换读取数据模式
        byteBuffer.flip();
        System.out.println("------------flip()----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 4.利用get()读取缓冲区中的数据
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes, 0, bytes.length));
        System.out.println("------------get()----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 5.rewind():可重复读
        byteBuffer.rewind();
        System.out.println("------------rewind()----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        // 6.clear():清空缓冲区，但是缓冲区的数据依然存在，但是这些数据处于"被遗忘的状态"
        byteBuffer.clear();
        System.out.println("------------clear()----------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        System.out.println((char)byteBuffer.get(2));
    }

    public static void main(String[] args) {
        aboutByteBuffer();
        System.out.println("------------BufferMark----------");
        bufferMark();
        System.out.println("----------------直接缓冲区--------------");
        directBuffer();
    }

}
