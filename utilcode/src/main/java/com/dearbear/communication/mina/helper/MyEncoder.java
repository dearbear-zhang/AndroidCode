package com.dearbear.communication.mina.helper;

import com.dearbear.communication.mina.bean.SocketMessage;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.ByteOrder;

public class MyEncoder extends ProtocolEncoderAdapter {
    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        // 业务层数据转socket数据
        SocketMessage msg = MessageConverUtil.userMsgToSocketMsg(o);
        IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put(SocketMessage.HEADER1);
        buffer.put(SocketMessage.HEADER2);
        buffer.put(msg.getType());
        byte[] body = msg.getBody();
        short bodyLength = (short) body.length;
        buffer.putShort(bodyLength);
        buffer.put(body);
        buffer.flip();
        protocolEncoderOutput.write(buffer);
    }
}
