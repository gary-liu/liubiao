package com.mimi.netty.server;

import com.mimi.netty.client.Time;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务器解码器
 * 连接建立时发送当前时间
 * @author liubiao
 */
public class TimeServerHandlerPOJO extends ChannelInboundHandlerAdapter {

   /*连接建立的时候并且准备进行通信时被调用*/
    @Override
    public void channelActive(final ChannelHandlerContext context) throws Exception{
        // 发送当前时间信息
        ChannelFuture f = context.writeAndFlush(new Time());
        // 发送完毕之后关闭 Channel
        f.addListener(ChannelFutureListener.CLOSE);

    }

    public void exceptionCaught(ChannelHandlerContext context, Throwable throwable) throws Exception {
        throwable.printStackTrace();;
        context.close();

    }
}
