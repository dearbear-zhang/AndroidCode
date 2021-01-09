package com.dearbear.communication.mina.helper;


import android.util.Log;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Mina服务端, 实现文件和消息的通信
 */
public class MinaServer implements Runnable {
	private static final String TAG = "HeartBeat";
	private int mPort;
	private IoAcceptor mAcceptor;
	private IoServiceListener mServiceListener;
	private IoHandlerAdapter mServerHandler;
	
	public MinaServer(int port, IoServiceListener serviceListener, IoHandlerAdapter serverHandler) {        
        mPort = port;
        mServiceListener = serviceListener;
        mServerHandler = serverHandler;
    }
	
    @Override
    public void run() {
        // 创建一个非阻塞的server端的Socket
    	mAcceptor = new NioSocketAcceptor();
        // 为接收器设置管理服务
    	mAcceptor.setHandler(mServerHandler);
    	mAcceptor.addListener(mServiceListener);
        // 自定义的编解码器
    	mAcceptor.getFilterChain().addLast("mycoder", new ProtocolCodecFilter(new MyCodecFactory(new MyEncoder(), new MyDecoder())));
        // 设置读取数据的换从区大小
    	mAcceptor.getSessionConfig().setReadBufferSize(MinaConstans.FILE_SEGMENT_SIZE + 100);
        // 绑定端口
        try {        	 
            Log.i(TAG,"socket server 开启");
            mAcceptor.bind(new InetSocketAddress(mPort));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void destroy() {
		Log.i(TAG, "shutdown acceptor!");
		if (mAcceptor != null) {
			Log.i(TAG, "acceptor dispose! " + mAcceptor.toString());
			mAcceptor.dispose();
		}
	}    
}
