package com.mimi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 输出接收到的消息
 * @author liubiao
 */
public class HellServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead (ChannelHandlerContext ctx,Object msg ) throws Exception {
        try {
            ByteBuf in = (ByteBuf)msg;
            System.out.print(in.toString((CharsetUtil.UTF_8)) );
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

    }

}
