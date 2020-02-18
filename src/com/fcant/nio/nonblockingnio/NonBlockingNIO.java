package com.fcant.nio.nonblockingnio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

/**
 * NonBlockingNIO
 * <p>
 * encoding:UTF-8
 *
 * 一、使用NIO完成网络通信的三个核心
 * 1.通道（Channel）：负责连接
 * java.nio.channels.Channel 接口：
 *     |--SelectableChannel
 *         |--SocketChannel
 *         |--SeverSocketChannel
 *         |--DatagramChannel
 *
 *         |--Pipe.SinkChannel
 *         |--Pipe.SourceChannel
 *
 * 2.缓冲区（Buffer）：负责数据的存取
 *
 * 3.选择器（Selector）：是SelectableChannel的多路复用器。用于监控SelectableChannel的IO状况
 *
 * @author Fcant 上午 10:39:39 2020/2/18/0018
 */
public class NonBlockingNIO {

    // DatagramChannel实现聊天室示例

    @Test
    public void send() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.next();
            byteBuffer.put((LocalDateTime.now().toString() + "SEND : " + input).getBytes());
            byteBuffer.flip();
            datagramChannel.send(byteBuffer, new InetSocketAddress("127.0.0.1", 9999));
            byteBuffer.clear();
        }
        datagramChannel.close();
    }

    @Test
    public void receive() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(9999));
        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);
        while (selector.select() > 0) {
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                if (selectionKey.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    datagramChannel.receive(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                    byteBuffer.clear();
                }
            }
            selectionKeyIterator.remove();
        }
    }

    // 非阻塞式实现聊天室

    @Test
    public void clientTest() throws IOException {
        // 1.获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        // 2.切换非阻塞模式
        socketChannel.configureBlocking(false);
        // 3.分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 4.发送数据给服务端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.next();
            byteBuffer.put((LocalDateTime.now().toString() + "发送内容：" + input).getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        // 5.关闭通道
        socketChannel.close();
    }

    @Test
    public void serverTest() throws IOException {
        // 1.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2.切换非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 3.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9999));
        // 4.获取选择器
        Selector selector = Selector.open();
        // 5.将通道注册到选择器上，并且指定“监听接收事件”
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 6.轮询式获取选择器中所有注册的“选择键（已就绪的监听事件）”
        while (selector.select() > 0) {
            // 7.获取当前选择器中所有注册的“选择键（已就绪的监听事件）”
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                // 8.获取准备就绪的事件
                SelectionKey selectionKey = selectionKeyIterator.next();
                // 9.判断具体是什么事件准备就绪
                if (selectionKey.isAcceptable()) {
                    // 10.若“接收就绪”，获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 11.切换非阻塞模式
                    socketChannel.configureBlocking(false);
                    // 12.将该通道注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    // 13.获取当前选择器上“读就绪”状态的通道
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // 14.读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while (((len = socketChannel.read(byteBuffer)) > 0)) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }
                // 15.取消选择键selectionKey
                selectionKeyIterator.remove();
            }
        }
    }
}
