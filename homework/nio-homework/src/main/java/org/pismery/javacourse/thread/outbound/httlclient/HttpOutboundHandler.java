package org.pismery.javacourse.thread.outbound.httlclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static java.nio.charset.StandardCharsets.*;

public class HttpOutboundHandler extends ChannelOutboundHandlerAdapter {
    private final String TARGET_SERVER;

    private static final Logger log = LoggerFactory.getLogger(HttpOutboundHandler.class);

    public HttpOutboundHandler(String targetServer) {
        this.TARGET_SERVER = targetServer.endsWith("/") ? targetServer.substring(0, targetServer.length() - 1) : targetServer;
    }

    public void handle(final ChannelHandlerContext ctx, FullHttpRequest fullRequest) {
        HttpClient client = buildHttpClient();

        HttpRequest request = buildHttpRequest(fullRequest);

        FullHttpResponse fullHttpResponse = null;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            fullHttpResponse = buildFullHttpResponse(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ctx.close();
        } finally {
            ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
            ctx.flush();
            ctx.close();
        }
    }

    private HttpRequest buildHttpRequest(FullHttpRequest request) {
        String url = TARGET_SERVER + request.uri();
        log.info("Remote URL: {}", url);
        HttpHeaders headers = request.headers();
        headers.iteratorAsString().forEachRemaining((entry) -> {
            log.info("Request Header => key={}, value={}",entry.getKey(),entry.getValue());
        });

        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
    }

    private HttpClient buildHttpClient() {
        return HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
    }

    private FullHttpResponse buildFullHttpResponse(HttpResponse<String> response) {
        byte[] body = response.body().getBytes(UTF_8);

        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

        fullHttpResponse.headers().set("Content-Type", "application/json");
        String len = response.headers().firstValue("content-length").orElse(String.valueOf(body.length));
        fullHttpResponse.headers().setInt("Content-Length", Integer.parseInt(len));

        return fullHttpResponse;
    }

}
