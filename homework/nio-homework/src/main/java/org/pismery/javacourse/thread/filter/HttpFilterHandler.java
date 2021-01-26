package org.pismery.javacourse.thread.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Collections;
import java.util.List;

public class HttpFilterHandler extends ChannelInboundHandlerAdapter {
    private static final List<HttpRequestFilter> filters = Collections.singletonList(
            new HttpRequestHeaderFilter()
    );


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;

        for (HttpRequestFilter filter : filters) {
            filter.filter(fullRequest, ctx);
        }

        //ctx.fireChannelRead(fullRequest);
        super.channelRead(ctx, fullRequest);
    }
}
