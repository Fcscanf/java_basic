package com.fcant.nio.pipe;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * AboutPipe
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 20:10:54 2020/2/18/0018
 */
public class AboutPipe {

    @Test
    public void pipeTest() throws IOException {
        // 1.获取管道
        Pipe pipe = Pipe.open();
        // 2.将缓冲区中的数据写入管道
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Pipe.SinkChannel sinkChannel = pipe.sink();
        byteBuffer.put("通过单向管道发送数据".getBytes());
        byteBuffer.flip();
        sinkChannel.write(byteBuffer);
        // 3.读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array(), 0, sourceChannel.read(byteBuffer)));
        sourceChannel.close();
        sinkChannel.close();
    }
}
