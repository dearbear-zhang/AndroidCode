package com.dearbear.communication.mina.helper;


import com.dearbear.communication.mina.bean.SocketMessage;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MyDecoder extends CumulativeProtocolDecoder {
    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        if (ioBuffer.remaining() >= 3) {
            ioBuffer.mark(); // 标记当前位置，方便reset
            byte[] header = new byte[2];
            ioBuffer.get(header, 0, header.length);
            if (header[0] == SocketMessage.HEADER1 && header[1] == SocketMessage.HEADER2) {
                byte type = ioBuffer.get();
                if (ioBuffer.remaining() >= 2) {
                    short bodyLength = ioBuffer.getShort();
                    if (ioBuffer.remaining() >= bodyLength) {
                        byte[] body = new byte[bodyLength];
                        ioBuffer.get(body, 0, bodyLength);
                        SocketMessage msg = new SocketMessage();
                        msg.setType(type);
                        msg.setBody(body);
                        // 解析socket数据到业务层
                        protocolDecoderOutput.write(MessageConverUtil.socketMsgToUserMsg(msg));
                        if (ioBuffer.remaining() >= 3) {
                            //再来一遍
                            return true;
                        }
                    } else {
                        //长度不够
                        ioBuffer.reset();
                    }
                } else {
                    //长度不够
                    ioBuffer.reset();
                }
            } else {
                System.err.println("HEADER[0] = " + header[0] + " , HEADER[1] = " + header[1]);
                throw new IllegalArgumentException("错误的HEADER");
            }
        }
        return false;
    }
}
