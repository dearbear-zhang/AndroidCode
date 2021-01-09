package com.dearbear.utilcode.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * 用于创建socket server
 * Create by dearbear
 * on 2021/1/9
 */
public class SocketServiceHelper {
    private static volatile SocketServiceHelper mInstance;
    private Consumer<String> mCallBack;
    private static final int PHONE_PORT = 10086;
    private ServerSocket mServerSocket;

    private SocketServiceHelper() {
        init();
    }

    public static SocketServiceHelper getInstance() {
        if (mInstance == null) {
            synchronized (SocketServiceHelper.class) {
                if (mInstance == null) {
                    mInstance = new SocketServiceHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * socket服务初始化
     */
    private void init() {
        new Thread(() -> {
            try {
                mServerSocket = new ServerSocket(PHONE_PORT);
                Socket client = null;
                ObjectInputStream ois = null;
                BufferedReader input = null;
                PrintWriter out = null;

                while (true) {
                    System.out.println("Server is Start");
                    client = mServerSocket.accept();
                    System.out.println("Get a connection from " + client.getRemoteSocketAddress().toString());
//                ois = new ObjectInputStream(client.getInputStream());
//                String somewords = (String) ois.readObject();
                    input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String somewords = input.readLine();
                    System.out.println("Get client data:" + somewords);
                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(client.getOutputStream())), true);
                    out.println("imei: null");
                    // 返回信息给调用者
                    if (mCallBack != null) {
                        mCallBack.accept(somewords);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }).start();

    }


    /**
     * @param callback socket消息回调函数
     */
    public void register(Consumer<String> callback) {
        mCallBack = callback;
    }
}
