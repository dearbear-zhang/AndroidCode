package com.dearbear.communication.mina.bean;

import java.io.Serializable;

public class SocketMessage implements Serializable {
    private static final short MAX_BODY_LENGTH = 1400;
    public static final byte HEADER1 = 0x5c;
    public static final byte HEADER2 = 0x74;

    /**
     * 定义消息类型
     */
    public static final byte TYPE_REQUEST_SEND_FILE = 1;
    public static final byte TYPE_ACCEPT_RECEIVE_FILE = 2;
    public static final byte TYPE_SEND_FILE_PART = 3;
    public static final byte TYPE_RECEIVE_FILE_FINISH = 4;
    public static final byte TYPE_RECEIVE_FILE_PART = 5;


    private byte type;
    private byte[] body;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte[] getBody() {
        return body.clone();
    }

    public void setBody(byte[] body) {
        this.body = body.clone();
    }
}
