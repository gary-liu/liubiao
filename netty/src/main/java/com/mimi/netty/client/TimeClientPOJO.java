package com.mimi.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import jdk.nashorn.internal.runtime.ECMAException;

import java.nio.charset.Charset;

/**
 * @author liubiao
 */
public class TimeClientPOJO {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port =8080;

        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new TimeDecoderPOJO(), new TimeClientHandlerPOJO());
                }
            });
            // 启动客户端，客户端用connect连接
            ChannelFuture f = b.connect(host,port).sync();

            /*f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {//连接成功
                        ByteBuf buf = Unpooled.copiedBuffer("hello", Charset.defaultCharset());//写数据
                        ChannelFuture wf = channelFuture.channel().writeAndFlush(buf);//发数据
                    }else {
                        //打印错误
                        Throwable throwable = channelFuture.cause();
                        throwable.printStackTrace();
                    }
                }
            });*/

            // 等待连接关闭
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }

    }

}
