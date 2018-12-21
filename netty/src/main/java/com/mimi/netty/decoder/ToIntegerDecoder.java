package com.mimi.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * 读取四字节，解码成整形
 * 继承于 ByteToMessageDecoder
 * @author liubiao
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_SIZE =1024;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() >= 4) { // int是4字节
            list.add(byteBuf.readInt());  // 添加到解码信息的List中
        }

        int readable = byteBuf.readableBytes();
        if (readable > MAX_FRAME_SIZE) {
            byteBuf.skipBytes(readable);
            throw new TooLongFrameException("帧数据超长");
        }

    }
}
