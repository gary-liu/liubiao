package com.mimi.netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author liubiao
 */
public class SimpleChatServer {

    private int port;

    public SimpleChatServer(int port) {
        this.port = port;
    }


    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();        // 用来接收进来的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();    // 用来处理已接收的连

        try {
            ServerBootstrap sb = new ServerBootstrap();            // 启动NIO服务的辅助启动类
            sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)                // 设置如何接受连接
                    .childHandler(new SimpleChatServerInitializer())    // 配置Channel
                    .option(ChannelOption.SO_BACKLOG, 128)                // 设置缓冲区
                    .childOption(ChannelOption.SO_KEEPALIVE, true);    // 启用心跳机制

            System.out.println("SimpleChatServer 启动了");
            ChannelFuture future = sb.bind(port).sync();        // 绑定端口，开始接收连接
            future.channel().closeFuture().sync();                // 等待关闭服务器（不会发生）
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("SimpleChatServer 关闭了");
        }

    }

    public static void main(String[] args) throws Exception {
        int port=8080;
        new SimpleChatServer(port).run();
    }


}
