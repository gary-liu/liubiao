package com.mimi.netty.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author liubiao
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    /**
       * 收到新的客户端连接时调用
      * 将客户端channel存入列表，并广播消息
     */
    public void hanlerAdded(ChannelHandlerContext context) throws Exception {
        Channel incoming = context.channel();
        // 广播加入消息
        channels.writeAndFlush("[SERVER]-"+incoming.remoteAddress()+"加入\n");
        channels.add(incoming);  // 存入列表
    }

    /**
     * 客户端连接断开时调用
     * 广播消息
     * @param context
     * @throws Exception
     */

    public void handlerRemoved(ChannelHandlerContext context) throws Exception {
        Channel incoming = context.channel();
        // 广播离开消息
        channels.writeAndFlush("[SERVER]-"+incoming.remoteAddress()+"离开\n");
        // channel会自动从ChannelGroup中删除

    }


    /**
     * 收到消息时调用
     * 将消息转发给其他客户端
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
            }else {
                channel.writeAndFlush("[you]" + s + "\n");
            }
        }

    }


    /**
     * 监听到客户端活动时调用
     * @param context
     * @throws Exception
     */
    public void chanelActive(ChannelHandlerContext context) throws  Exception{
        Channel incoming = context.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "在线");

    }
    /**
     * 监听到客户端不活动时调用
     * @param context
     * @throws Exception
     */
    public void chanelInactive(ChannelHandlerContext context) throws  Exception{
        Channel incoming = context.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "掉线");

    }


    public void exceptionCaught(ChannelHandlerContext context, Throwable throwable) throws Exception {
        Channel incoming = context.channel();

        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "异常");

    }

}
