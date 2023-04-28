package com.example.demo.io;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaoxu123
 * @deprecated Bio的实现
 */
public class BIO {

    private static boolean stop=false;

    public static void main(String[] args) throws Exception{
        int connectionNum=0;
        int port=8080;

        ExecutorService executorService = Executors.newFixedThreadPool( 10 );
        ServerSocket serverSocket = new ServerSocket(port);

        while(!stop){
            if(10==connectionNum){
                stop=true;
            }

            Socket socket = serverSocket.accept();
            executorService.execute(()->{
                try {
                    Scanner scanner = new Scanner(socket.getInputStream() );
                    PrintStream printStream = new PrintStream(socket.getOutputStream());
                    while(!stop){
                        String trim = scanner.next().trim();
                        printStream.println("PONG"+trim);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            connectionNum++;
            executorService.shutdown();
            serverSocket.close();
        }

    }
}
