package org.pismery.javacourse.fx.homework.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class EventBusConfig {

    @Bean
    @SuppressWarnings("UnstableApiUsage")
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    @SuppressWarnings("UnstableApiUsage")
    public AsyncEventBus asyncEventBus() {
        return new AsyncEventBus("AsyncEventBus", eventBusExecutor());
    }

    @Bean
    public Executor eventBusExecutor() {
        return Executors.newFixedThreadPool(10);
    }


}
