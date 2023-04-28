package com.example.springdataelastic.nio;

import com.example.springdataelastic.client.ClientThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Nio聊天室客户端
 * @author xiaoxu123
 */
public class NioClient {
    public static void main(String[] args) {
        // 当前客户端的用户名
        String username="xiaoxu";
        // 为当前客户端开辟一个线程
        ClientThread client = new ClientThread( username);
        client.start();
        // 输入输出流
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( System.in ) );
        // 循环读取键盘输入
            try {
                // 循环读取键盘输入
                String readline;
                if((readline=bufferedReader.readLine())!=null){
                   if(readline.equals("bye")){
                     client.close();
                     System.exit(0);
                   }
                    // 发送消息
                    client.send(username + ":" + readline);
                }
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
