package com.mimi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 分隔符协议
 *
 * @author liubiao
 */

public class CmdHandlerInit extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 添加解码器，
        pipeline.addLast(new CmdDecoder(65 * 1024));
        pipeline.addLast(new CmdHandler());
    }

    public static final class Cmd {
        private final ByteBuf name;    // 名字
        private final ByteBuf args;    // 参数

        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

        public ByteBuf name() {
            return name;
        }

        public ByteBuf args() {
            return args;
        }
    }

    /**
     * 根据分隔符将消息解码成Cmd对象传给下一个处理器
     */
    public static final class CmdDecoder extends LineBasedFrameDecoder {

        public CmdDecoder(int maxLength) {
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            // 通过结束分隔符从 ByteBuf 提取帧
            ByteBuf frame = (ByteBuf)super.decode(ctx, buffer);
            if(frame == null)
                return null;
            int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), (byte)' ');
            // 提取 Cmd 对象
            return new Cmd(frame.slice(frame.readerIndex(), index),
                    frame.slice(index+1, frame.writerIndex()));
        }
    }

    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Cmd msg) throws Exception {
            // 处理 Cmd 信息
        }

    }
}
