package org.pismery.javacourse.fx.homework.service;

import com.google.common.eventbus.EventBus;
import org.pismery.javacourse.fx.homework.annotation.EventBusListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class EventBusService implements InitializingBean {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private EventBus eventBus;


    public void postEvent(Object event) {
        eventBus.post(event);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        context.getBeansWithAnnotation(EventBusListener.class).forEach((name, bean) -> {
            eventBus.register(bean);
        });
    }


}
