package org.pismery.javacourse.rpc.core.client;


import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pismery.javacourse.rpc.core.api.RpcfxRequest;
import org.pismery.javacourse.rpc.core.api.RpcfxResponse;
import org.pismery.javacourse.rpc.core.http.NettyClient;
import org.pismery.javacourse.rpc.core.http.OkHttpClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class JDKRpcfx {

    static {
        //for fast json
        ParserConfig.getGlobalInstance().addAccept("org.pismery");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {
        // 0. 替换动态代理 -> AOP
        return (T) Proxy.newProxyInstance(JDKRpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));
    }

    public static class RpcfxInvocationHandler implements InvocationHandler {
        private static final ObjectMapper objectMapper = new ObjectMapper();

        private final Class<?> serviceClass;
        private final String url;

        public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
        // int byte char float double long bool
        // [], data class

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
            RpcfxRequest request = new RpcfxRequest();
            request.setServiceClass(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(params);

            RpcfxResponse response = post(request, url);

            // 这里判断response.status，处理异常
            // 考虑封装一个全局的RpcfxException => done
            if (!response.isStatus()) {
                throw response.getException();
            }

            return response.getResult();
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = objectMapper.writeValueAsString(req);
            System.out.println("req json: " + reqJson);

            // 1.可以复用client
            // 2.尝试使用httpclient或者netty client => done
            String respJson = NettyClient.request(url, reqJson);
            System.out.println("resp json: " + respJson);

            return objectMapper.readValue(respJson, RpcfxResponse.class);
        }

    }
}
