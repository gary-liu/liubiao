package com.mimi.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 *
 * * WebSocket
 * WebSocketServerProtocolHandler 处理其他类型帧
 * TextFrameHandler BinaryFrameHandler ContinuationFrameHandler
 * @author liubiao
 */
public class WebSocketServerInit extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
               new HttpServerCodec(),
               new HttpObjectAggregator(65536),        // HTTP 聚合
                  // 处理除指定Frame之外的其他类型帧，比如Ping,Pong,Close等
               new WebSocketServerProtocolHandler("/websocket"),
               new TextFrameHandler(),
               new BinaryFrameHandler(),
               new ContinuationFrameHandler());
    }
    // Text Frame
    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        }
    }

    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {

        }
    }

    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationFrameHandler> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ContinuationFrameHandler continuationFrameHandler) throws Exception {

        }
    }






}
