package com.example.demo.io;

import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author xiaoxu123
 * @deprecated Nio客户端实现
 */
public class NioClient {

    public static void main(String[] args) throws Exception{
        System.out.println(">>>>>>>...NIO客户端启动...>>>>>>>>");
        //创建一个Tcp连接的通道并指定地址通道
        SocketChannel channel = SocketChannel.open( new InetSocketAddress( "127.0.0.1", 8082) );

        // 2.将通道置为非阻塞模式
        channel.configureBlocking(false);

        // 3.创建字节缓冲区，并写入要传输的消息数据
        ByteBuffer buffer = ByteBuffer.allocate( 1024 );

        String msg="蚂蚁国际超网接口开放平台";

        buffer.put(msg.getBytes());

        // 4.将缓冲区切换为读取模式
        buffer.flip();
        // 5.将带有数据的缓冲区写入通道，利用通道传输数据
        channel.write(buffer);
        // 6.传输完成后情况缓冲区、关闭通道
        buffer.clear();
        channel.close();

    }

}
