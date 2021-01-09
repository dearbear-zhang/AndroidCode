package com.dearbear.communication.mina.helper;

import com.google.gson.Gson;
import com.dearbear.communication.mina.bean.AcceptReceiveFileMsg;
import com.dearbear.communication.mina.bean.FilePartMsg;
import com.dearbear.communication.mina.bean.ReceiveFileFinishMsg;
import com.dearbear.communication.mina.bean.RequestSendFileMsg;
import com.dearbear.communication.mina.bean.SocketMessage;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class MessageConverUtil {

    /***
     * socket消息装换为业务消息
     * @param socketMessage
     * @return
     */
    public static Object socketMsgToUserMsg(SocketMessage socketMessage) {
        Object userMsg = null;
        switch (socketMessage.getType()) {
            case SocketMessage.TYPE_REQUEST_SEND_FILE:
                userMsg = jsonToBean(socketMessage.getBody(), RequestSendFileMsg.class);
                break;
            case SocketMessage.TYPE_ACCEPT_RECEIVE_FILE:
                userMsg = jsonToBean(socketMessage.getBody(), AcceptReceiveFileMsg.class);
                break;
            case SocketMessage.TYPE_SEND_FILE_PART:
                byte[] body = socketMessage.getBody();
                ByteBuffer buffer = ByteBuffer.allocate(body.length);
                buffer.put(body);
                buffer.flip();
                short jsonLength = buffer.getShort();
                byte[] jsonBody = new byte[jsonLength];
                buffer.get(jsonBody, 0, jsonLength);
                userMsg = jsonToBean(jsonBody, FilePartMsg.class);
                int dataLength = body.length - 2 - jsonLength;
                byte[] data = new byte[dataLength];
                buffer.get(data, 0, dataLength);
                ((FilePartMsg) userMsg).setData(data);
                break;
            case SocketMessage.TYPE_RECEIVE_FILE_FINISH:
                userMsg = jsonToBean(socketMessage.getBody(), ReceiveFileFinishMsg.class);
                break;
            default:
                break;
        }
        return userMsg;
    }

    /***
     * 业务消息装换为socket消息
     * @param o
     * @return
     */
    public static SocketMessage userMsgToSocketMsg(Object o) {
        SocketMessage message = new SocketMessage();
        if (o instanceof RequestSendFileMsg) {
            message.setType(SocketMessage.TYPE_REQUEST_SEND_FILE);
            message.setBody(beanToJsonByte(o));
        } else if (o instanceof AcceptReceiveFileMsg) {
            message.setType(SocketMessage.TYPE_ACCEPT_RECEIVE_FILE);
            message.setBody(beanToJsonByte(o));
        } else if (o instanceof FilePartMsg) {
            message.setType(SocketMessage.TYPE_SEND_FILE_PART);
            FilePartMsg filePartMsg = (FilePartMsg) o;
            byte[] jsonBody = beanToJsonByte(o);
            ByteBuffer buffer = ByteBuffer.allocate(jsonBody.length + 2 + (filePartMsg.getData().length));
            buffer.putShort((short) jsonBody.length);
            buffer.put(jsonBody);
            buffer.put(filePartMsg.getData());
            message.setBody(buffer.array());
        } else if (o instanceof ReceiveFileFinishMsg) {
            message.setType(SocketMessage.TYPE_RECEIVE_FILE_FINISH);
            message.setBody(beanToJsonByte(o));
        }
        return message;
    }

    private static <T> Object jsonToBean(byte[] body, Class<T> tClass) {
        return new Gson().fromJson(new String(body, Charset.forName("UTF-8")), tClass);
    }

    private static byte[] beanToJsonByte(Object o) {
        return new Gson().toJson(o).getBytes(Charset.forName("UTF-8"));
    }
}
