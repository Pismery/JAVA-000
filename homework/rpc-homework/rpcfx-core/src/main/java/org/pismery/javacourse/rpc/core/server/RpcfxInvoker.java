package org.pismery.javacourse.rpc.core.server;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pismery.javacourse.rpc.core.api.ApiBean;
import org.pismery.javacourse.rpc.core.api.RpcfxRequest;
import org.pismery.javacourse.rpc.core.api.RpcfxResolver;
import org.pismery.javacourse.rpc.core.api.RpcfxResponse;
import org.pismery.javacourse.rpc.core.exception.RpcfxException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver){
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();

        try {
            // 作业1：改成泛型和反射 => done
            Object service = resolver.resolve(serviceClass);
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,

            // 两次json序列化能否合并成一个 => done
            response.setResult((ApiBean) result);
            response.setStatus(true);
            return response;
        } catch (IllegalAccessException | InvocationTargetException e) {

            // 3.Xstream

            // 2.封装一个统一的RpcfxException => done
            // 客户端也需要判断异常
            response.setException(new RpcfxException(e));
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        Optional<Method> method = Arrays.stream(klass.getMethods())
                .filter(m -> methodName.equals(m.getName()))
                .findFirst();

        if (!method.isPresent()) {
            throw new RpcfxException("Method not found.");
        }

        return method.get();
    }

}
