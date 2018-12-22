package com.mimi.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 *
 * HTTPS
 * @author liubiao
 */
public class HttpsCodesInit extends ChannelInitializer<Channel> {
    private final SslContext context;
    private final boolean client;

    public HttpsCodesInit(SslContext context, boolean client) {
        this.context = context;
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        SSLEngine engine = context.newEngine(channel.alloc());
        // 添加SslHandler以启用HTTPS

        pipeline.addFirst("ssl", new SslHandler(engine));
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());

        }else {
            pipeline.addLast("codec", new HttpServerCodec());

        }


    }
}
