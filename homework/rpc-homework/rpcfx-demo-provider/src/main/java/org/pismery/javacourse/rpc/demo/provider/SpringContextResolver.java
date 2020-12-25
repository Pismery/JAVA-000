package org.pismery.javacourse.rpc.demo.provider;

import org.pismery.javacourse.rpc.core.api.RpcfxResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpringContextResolver implements RpcfxResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static final Map<String, Object> beanMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object resolve(String serviceClass) {
        Object result = beanMap.get(serviceClass);
        try {
            if (null == result) {
                result = applicationContext.getBean(Class.forName(serviceClass));
                beanMap.putIfAbsent(serviceClass, result);
            }
            return result;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
