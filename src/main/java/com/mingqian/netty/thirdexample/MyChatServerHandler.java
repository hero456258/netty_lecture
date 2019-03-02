package com.mingqian.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String>{

    //保存channel对象
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach( e ->{
            if(channel != e){
                e.writeAndFlush(channel.remoteAddress() + "发送的消息" + msg + "\n");
            }else {
                e.writeAndFlush("【自己】" + msg + "\n");
            }
        });


    }

    /***
     * 客户端与服务器建立好回调该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //1.获取通道对象
        Channel channel = ctx.channel();
        //2向其他所有客户端广播
        channelGroup.writeAndFlush("【服务器】" + channel.remoteAddress() + "加入\n");
        //3.将新连接的channel加入到通道组对象，因为没必要自己上线通知自己，所以这行代码为什么没有在上一行代码上面
        channelGroup.add(channel);

    }

    /**
     * 客户端与服务器断掉连接时回调该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】" + channel.remoteAddress() + "退出连接\n");
        System.out.println(channelGroup.size());
    }

    /**
     * 连接处于活动状态回调该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
