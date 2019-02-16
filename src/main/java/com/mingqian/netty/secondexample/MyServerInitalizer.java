package com.mingqian.netty.secondexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyServerInitalizer extends ChannelInitializer<SocketChannel>{

    /**
     * initChannel客户端和服务端一旦连接之后，MyServerInitalizer这个对象就会被创建，
     * initChannel这个方法就会被调用。
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //添加一解码器
        pipeline.addLast("lengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        //添加一个编码器
        pipeline.addLast("lengthFieldPrepender", new LengthFieldPrepender(4));
        //添加一个字符串解码器
        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        //添加一个字符串编码器
        pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
        //添加自定义的处理器handler
        pipeline.addLast(new MyServerHandler());

    }
}
