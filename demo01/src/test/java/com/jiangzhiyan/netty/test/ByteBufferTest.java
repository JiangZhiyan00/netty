package com.jiangzhiyan.netty.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class ByteBufferTest {

    @Test
    public void fileChannelTest() {
        //准备输入流
        try (FileChannel channel = new FileInputStream("src/test/resources/data.txt").getChannel()) {
            //准备缓冲区,指定缓冲区大小为10个字节
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while(true) {
                //从Channel中读取缓冲区大小的数据,向buffer写入
                //返回实际读取到的字节数,返回-1则代表没有内容了
                int len = channel.read(buffer);
                log.debug("读取到的字节数:{}",len);
                if (len == -1) {
                    log.debug("读取完毕");
                    break;
                }
                //打印buffer内容
                buffer.flip();//切换至读模式
                //.hasRemaining():是否还有剩余未读数据
                while(buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.debug("实际字节:{}", (char) b);
                }
                //切换为写模式
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allocateTest(){
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(16);
        //class java.nio.HeapByteBuffer
        log.debug(buffer1.getClass().toString());
        //class java.nio.DirectByteBuffer
        log.debug(buffer2.getClass().toString());
    }
}
