package com.mimi.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 *
 *  HTTP 压缩
 * HttpContentDecompressor 用于客户端解压缩
 * HttpContentCompressor 用于服务器压缩
 * @author liubiao
 */
public class HttpCompressorInit extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpCompressorInit(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        if (client) {
            // 客户端
            pipeline.addLast("codec", new HttpClientCodec());
            // 解压缩，用于处理来自服务器的压缩内容
            pipeline.addLast("decompresssor", new HttpContentDecompressor());

        }else {
            // 服务端
            pipeline.addLast("codec", new HttpServerCodec());
            // 压缩，将要发送的消息压缩后再发出
            pipeline.addLast("compressor", new HttpContentCompressor());


        }

    }
}
