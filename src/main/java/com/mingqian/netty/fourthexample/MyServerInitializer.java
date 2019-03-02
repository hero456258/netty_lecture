package com.mingqian.netty.fourthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServerInitializer  extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //空闲状态检测处理器:有三个参数：多久没读多久写多久没读写都会触发该处理器
        pipeline.addLast(new IdleStateHandler(5, 7,3, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
