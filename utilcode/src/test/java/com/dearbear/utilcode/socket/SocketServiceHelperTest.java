package com.dearbear.utilcode.socket;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Create by dearbear
 * on 2021/1/9
 */
public class SocketServiceHelperTest {

    @Test
    public void register() {
        String sendMessage = "socket 连接测试";

        SocketServiceHelper.getInstance().register(s -> {
            System.out.println(s);
            assertEquals(s, sendMessage);
        });
        SocketClientHelper.client(sendMessage);

    }
}