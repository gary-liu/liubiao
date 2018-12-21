package com.mimi.netty.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 *  加密的入站数据被 SslHandler 拦截，进行解密
 数据被解密后，原始数据入站
 原始数据经过 SslHandler
 SslHandler 加密数据并传递数据出站
 *
 * @author liubiao
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    private final boolean client;
    private final boolean startTls;// 第一次请求是否加密

    public SslChannelInitializer(SslContext context, boolean client, boolean startTls) {
        this.context = context;
        this.client = client;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        // 给每个 SslHandler 实例使用一个新的 SslEngine
        SSLEngine engine = context.newEngine(channel.alloc());
        engine.setUseClientMode(client);// 设置SslEngine是client或者是server模式
        // 添加SslHandler到pipeline作为第一个处理器
        channel.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
    }
}
