package org.pismery.javacourse.rpc.core.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.pismery.javacourse.rpc.core.api.RpcfxRequest;
import org.pismery.javacourse.rpc.core.api.RpcfxResponse;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ByteBuddyRpcfx {

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> serviceClass, final String url) {
        T result = null;
        try {
            result = (T) new ByteBuddy()
                    .subclass(Object.class)
                    .name(serviceClass.getName() + "$ByteBuddy")
                    .implement(serviceClass)
                    .intercept(InvocationHandlerAdapter.of(new RpcfxInvocationHandler(serviceClass, url)))
                    .make()
                    .load(serviceClass.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static class RpcfxInvocationHandler implements InvocationHandler {
        private static final ObjectMapper objectMapper = new ObjectMapper();

        private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

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
            // 2.尝试使用httpclient或者netty client
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSON_TYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: " + respJson);

            return objectMapper.readValue(respJson, RpcfxResponse.class);
        }
    }
}
