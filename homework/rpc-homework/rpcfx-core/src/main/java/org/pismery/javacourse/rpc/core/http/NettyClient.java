package org.pismery.javacourse.rpc.core.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import okhttp3.MediaType;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyClient {
    private static final Map<ChannelId, String> CHANNEL_RESP_MAP = new ConcurrentHashMap<>();

    public static String request(String urlStr, String requestBody) throws IOException {
        final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        String result;
        URL url = new URL(urlStr);
        try {
            Bootstrap bootstrap = new Bootstrap();
            Channel channel = bootstrap.group(eventLoopGroup)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new HttpClientCodec());
                            socketChannel.pipeline().addLast(new HttpObjectAggregator(65536));
                            socketChannel.pipeline().addLast(new HttpContentDecompressor());
                            socketChannel.pipeline().addLast(new RpcClientHandler(requestBody));
                        }
                    })
                    .connect(url.getHost(), url.getPort())
                    .syncUninterruptibly()
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println("---------------------------连接成功--------------------------");
                        }
                    })
                    .channel();

            ChannelId id = channel.id();
            channel.closeFuture().syncUninterruptibly();
            result = CHANNEL_RESP_MAP.get(id);
            CHANNEL_RESP_MAP.remove(id);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

        return result;
    }

    public static class RpcClientHandler extends ChannelInboundHandlerAdapter {
        private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

        private String requestBody;

        RpcClientHandler(String requestBody) {
            this.requestBody = requestBody;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(requestBody.getBytes());
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/", byteBuf);
            request.headers().add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
            request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            request.headers().add(HttpHeaderNames.HOST, "127.0.0.1");
            request.headers().set(HttpHeaderNames.CONTENT_TYPE, JSON_TYPE);
            ctx.writeAndFlush(request);
        }


        @Override
        public void channelRead(ChannelHandlerContext ctx, Object message) {
            System.out.println("----------------------获取响应数据-----------------------");
            FullHttpResponse response = (FullHttpResponse) message;
            ByteBuf buf = response.content();
            String result = buf.toString(CharsetUtil.UTF_8);

            CHANNEL_RESP_MAP.putIfAbsent(ctx.channel().id(), result);
        }
    }
}
