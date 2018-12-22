package com.mimi.netty.chat.client;

/**
 * @author liubiao
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 客户端
 * 开启客户端，接收控制台输入并发送给服务端
 */
public class SimpleChatClient {
    private final String host;        // IP
    private final int port;        // 端口

    public SimpleChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // 配置并运行客户端
    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();        // 客户端辅助启动类
            b.group(group)                                    // 客户端只需要一个用来接收并处理连接
                    .channel(NioSocketChannel.class)            // 设置如何接受连接
                    .handler(new SimpleChatClientInitializer());// 配置 channel
            // 连接服务器
            Channel channel = b.connect(host, port).sync().channel();
            // 读取控制台输入字符
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                // 每行成一帧输出，以"\r\n"结尾
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();        // 输出异常
        } finally {
            group.shutdownGracefully();    // 关闭
        }
    }

    public static void main(String[] args) throws Exception {
        new SimpleChatClient("localhost", 8080).run();        // 启动客户端
    }

}
