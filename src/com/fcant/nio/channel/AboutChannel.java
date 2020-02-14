package com.fcant.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * AboutChannel
 * <p>
 * encoding:UTF-8
 *
 * 一、通道（Channel）：用于源节点与目标节点的连接。在Java NIO中负责缓冲区中的数据传输，
 * Channel本身不存储数据，因此需要配合缓冲区进行传输
 *
 * 二、通道的主要实现类
 * java.nio.channels.Channel接口：
 *      |--FileChannel          --|本地IO
 *      |--SocketChannel        ----|TCP  --|
 *      |--ServerSocketChannel  ----|     --|网络IO
 *      |--DatagramChannel      --|UDP    --|
 *
 * 三、获取通道
 * 1、 Java针对支持通道的类提供了getChannel()方法
 *      本地IO：
 *      FileInputStream/FileOutputStream
 *      RandomAccessFile
 *
 *      网络IO：
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *
 * 2、在jdk7中的NIO.2针对各个通道提供了静态方法open()
 * 3、在jdk7中的NIO.2的File工具类的newByteChannel()
 *
 * @author Fcant 下午 20:02:38 2020/2/11/0011
 */
public class AboutChannel {

    public static void main(String[] args) {
        copyFileByChannel();
    }

    // 使用通道完成文件的复制-非直接缓冲区
    public static void copyFileByChannel() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileInputStreamChannel = null;
        FileChannel fileOutputStreamChannel = null;
        try {
            fileInputStream = new FileInputStream("1.jpg");
            fileOutputStream = new FileOutputStream("2.jpg");
            // 1、获取通道
            fileInputStreamChannel = fileInputStream.getChannel();
            fileOutputStreamChannel = fileOutputStream.getChannel();

            // 2、分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            // 3、将通道中的数据存入缓冲区中
            while (fileInputStreamChannel.read(byteBuffer) != -1) {
                // 切换读数据模式
                byteBuffer.flip();
                // 4、将缓冲区中的数据写入通道中
                fileOutputStreamChannel.write(byteBuffer);
                // 清空缓冲区
                byteBuffer.clear();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStreamChannel != null) {
                try {
                    fileOutputStreamChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStreamChannel != null) {
                try {
                    fileInputStreamChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
