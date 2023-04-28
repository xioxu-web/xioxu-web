package com.example.demo.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * @author xiaoxu123
 */
@Slf4j
public class BioClient {

    public static void main(String[] args)throws Exception {
        System.out.println(">>>>>>>...BIO客户端启动...>>>>>>>>");
        //创建Socket并根据IP和port连接服务端
        Socket socket = new Socket( "127.0.0.1", 8080);

        // 2.从Socket对象中获取一个字节输出流并封装成输出对象
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new PrintStream( outputStream );

        //通过输出对象网服务端发送消息
        printStream.println("hello 我是xiaoxu");

        socket.shutdownOutput();

        // 5.从套接字中获取字节输入流并封装成输入对象
        InputStream inputStream = socket.getInputStream();

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );

        // 6.通过输入对象从Buffer读取信息
        String msg;
        while((msg=bufferedReader.readLine())!=null){
            System.out.println("收到信息：" + msg);
        }
        // 7.发送后清空输出流中的信息
        printStream.flush();
        // 8.使用完成后关闭流对象与套接字
        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
