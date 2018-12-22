package com.mimi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author liubiao
 * 基于长度的协议
 * LengthFieldBasedFrameDecoder
 */
public class LineBasedHandlerInit extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 用于提取基于帧编码长度8个字节的帧
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65*1024, 0, 8));
        pipeline.addLast(new FrameHandler());
    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            // TODO 数据处理
        }

    }

}
