package com.mimi.netty.server;

import com.mimi.netty.client.Time;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 服务器数据编码类
 * @author liubiao
 */
public class TimeEncoderPOJO extends MessageToByteEncoder<Time> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Time time, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt((int) time.getValue());
    }
}
