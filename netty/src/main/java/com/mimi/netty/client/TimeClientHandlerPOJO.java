package com.mimi.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author liubiao
 */
public class TimeClientHandlerPOJO extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        Time time =(Time)msg;
        System.out.println(time);
        context.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws  Exception {
        cause.printStackTrace();
        context.close();

    }

}
