package com.mimi.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author liubiao
 */
public class TimeDecoderPOJO extends ByteToMessageDecoder{

    /**
     * 有新数据接收时调用
     * 为防止分包现象，先将数据存入内部缓存，到达满足条件之后再进行解码
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return ;
        }
        list.add(new Time(byteBuf.readUnsignedInt()));
    }
}
