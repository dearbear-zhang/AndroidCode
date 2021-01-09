package com.dearbear.communication.mina.helper;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MyCodecFactory implements ProtocolCodecFactory {
    ProtocolEncoder mEncoder;
    ProtocolDecoder mDdcoder;

    public MyCodecFactory(ProtocolEncoder encoder, ProtocolDecoder decoder) {
        mEncoder = encoder;
        mDdcoder = decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return mEncoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return mDdcoder;
    }
}
