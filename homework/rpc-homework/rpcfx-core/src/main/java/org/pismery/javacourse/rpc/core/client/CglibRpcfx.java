package org.pismery.javacourse.rpc.core.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.pismery.javacourse.rpc.core.api.RpcfxRequest;
import org.pismery.javacourse.rpc.core.api.RpcfxResponse;

import java.io.IOException;
import java.lang.reflect.Method;

public class CglibRpcfx {

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> serviceClass, final String url) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setCallback(new CglibMethodInterceptor(serviceClass, url));
        return (T) enhancer.create();
    }

    public static class CglibMethodInterceptor implements MethodInterceptor {
        private static final ObjectMapper objectMapper = new ObjectMapper();
        private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;

        public <T> CglibMethodInterceptor(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
            RpcfxRequest request = new RpcfxRequest();
            request.setMethod(method.getName());
            request.setServiceClass(serviceClass.getName());
            request.setParams(params);

            RpcfxResponse response = post(request, url);

            if (!response.isStatus()) {
                throw response.getException();
            }

            return response.getResult();
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = objectMapper.writeValueAsString(req);
            System.out.println("req json: "+reqJson);

            // 1.可以复用client
            // 2.尝试使用httpclient或者netty client
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSON_TYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: "+respJson);

            return objectMapper.readValue(respJson, RpcfxResponse.class);
        }
    }
}
