package com.dearbear.utilcode.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Create by dearbear
 * on 2021/1/9
 */
public class SocketClientHelper {
    /**
     * 端口号
     */
    private static final int PHONE_PORT = 10086;
    public static final String HOST = "localhost";

    public static void client(String message) {
        try {
            Socket socket = new Socket(HOST, PHONE_PORT);
            //读取服务器端数据
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //向服务器端发送数据
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println(message);

            String ret = input.readLine();
            System.out.println("服务器端返回过来的是: " + ret);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
