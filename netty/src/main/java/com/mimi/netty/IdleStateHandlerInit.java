package com.mimi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.omg.CORBA.TIMEOUT;

import java.util.concurrent.TimeUnit;

/**
 * 空闲连接以及超时
 *
 * @author liubiao
 */
public class IdleStateHandlerInit extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 若60s没有收到消息，调用userEventTriggered方法
        pipeline.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
        pipeline.addLast(new HeartBeatHandle());


    }

    public static final class HeartBeatHandle extends ChannelInboundHandlerAdapter{
        private static final ByteBuf HEARTABEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.UTF_8)
        );

        public void userEventTriggered(ChannelHandlerContext context ,Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                //发送心跳到远端
                context.writeAndFlush(HEARTABEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE); // 关闭连接
            }else {
                // 传递给下一个处理程序
                super.userEventTriggered(context, evt);
            }
        }


    }
}
