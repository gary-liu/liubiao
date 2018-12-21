package com.mimi.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author liubiao
 */
public class TimeServer {
    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public void run() throws  Exception {
        EventLoopGroup bossgroup = new NioEventLoopGroup(); // 用来接收进来的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 用来处理已经被接收的连接
        System.out.println("准备运行端口:" + port);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossgroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 这里告诉Channel如何接收新的连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 自定义处理类
                            socketChannel.pipeline().addLast(new TimeEncoderPOJO(), new TimeServerHandlerPOJO());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();
            // 等待服务器socket关闭
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
            workerGroup.shutdownGracefully();
            bossgroup.shutdownGracefully();

        }

    }

    public static void main(String[] args) throws Exception {
        int port =8080;
        new TimeServer(port).run();
    }


}
