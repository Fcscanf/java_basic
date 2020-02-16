package com.fcant.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

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
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 *
 * 五、分散（Scatter）与聚集（Gather）
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 *
 * 六、字符集
 * 编码：字符串 -> 字节数组
 * 解码：字节数组 -> 字符串
 *
 * @author Fcant 下午 20:02:38 2020/2/11/0011
 */
public class AboutChannel {

    public static void main(String[] args) throws IOException {
        aboutCharSet();
        System.out.println("----------编码和解码--------");
        charSetEncodeDeCode();
    }

    /**
     * 编码以及解码示例
     *
     * @author Fcant 下午 21:03:45 2020/2/16/0016
     */
    public static void charSetEncodeDeCode() throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");
        // 获取编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();
        // 获取解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("樊乘乘");
        charBuffer.flip();
        // 编码
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);
        for (int i = 1; i < 7; i++) {
            System.out.println(byteBuffer.get());
        }
        // 解码
        byteBuffer.flip();
        CharBuffer decode = charsetDecoder.decode(byteBuffer);
        System.out.println(decode.toString());

        System.out.println("-----------------以UTF-8编码格式进行解码----------------");
        Charset charsetUTF8 = Charset.forName("UTF-8");
        byteBuffer.flip();
        CharBuffer charBufferUTF8 = charsetUTF8.decode(byteBuffer);
        System.out.println(charBufferUTF8.toString());
    }

    /**
     * 字符集
     *
     * @author Fcant 下午 20:32:16 2020/2/15/0015
     */
    public static void aboutCharSet() {
        Map<String, Charset> charsetMap = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entrySet = charsetMap.entrySet();
        for (Map.Entry<String, Charset> stringCharsetEntry : entrySet) {
            System.out.println(stringCharsetEntry.getKey() + "=" + stringCharsetEntry.getValue());
        }
    }

    /**
     * 分散读取和聚集写入
     *
     * @author Fcant 下午 21:31:30 2020/2/14/0014
     */
    public static void scatterAndGather() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        // 1.获取通道
        FileChannel fileChannel = randomAccessFile.getChannel();
        // 2.分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
        // 3.分散读取
        ByteBuffer[] buffers = {byteBuffer, byteBuffer1};
        fileChannel.read(buffers);
        for (ByteBuffer buffer : buffers) {
            buffer.flip();
        }
        System.out.println(new String(buffers[0].array(), 0, buffers[0].limit()));
        System.out.println("------------------");
        System.out.println(new String(buffers[1].array(), 0, buffers[1].limit()));

        // 4.聚集写入
        RandomAccessFile accessFile = new RandomAccessFile("2.txt", "rw");
        FileChannel accessFileChannel = accessFile.getChannel();
        accessFileChannel.write(buffers);
    }

    // 通道之间的数据传输（直接缓冲区）
    public static void channelDataTrans() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("4.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        // inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("直接缓冲区方式-通道数据传输数据复制文件-总耗费时间为：" + (end - start));
    }

    // 使用直接缓冲区完成文件的复制（内存映射文件）
    public static void copyFileByMappedByteBuffer() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        // 内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 直接对缓冲区进行数据的读写操作
        byte[] bytes = new byte[inMappedBuf.limit()];
        inMappedBuf.get(bytes);
        outMappedBuf.put(bytes);

        inChannel.close();
        outChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("直接缓冲区总耗费时间为：" + (end - start));
    }

    // 使用通道完成文件的复制-非直接缓冲区
    public static void copyFileByChannel() {
        long start = System.currentTimeMillis();
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
        long end = System.currentTimeMillis();
        System.out.println("非直接缓冲区总耗费时间为：" + (end - start));
    }
}
