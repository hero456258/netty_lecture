package com.mingqian.netty.fiifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by wanggang on 2019/3/3.
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //块写处理器
        pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
        //http消息聚合处理器
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));
        //webSocket协议服务器处理
        pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(null);

    }
}
