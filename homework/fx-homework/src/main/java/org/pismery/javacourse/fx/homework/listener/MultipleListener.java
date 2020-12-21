package org.pismery.javacourse.fx.homework.listener;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.pismery.javacourse.fx.homework.annotation.EventBusListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EventBusListener
public class MultipleListener {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        MultipleListener listener = new MultipleListener();
        eventBus.register(listener);

        eventBus.post((Number) 1);
        eventBus.post(2L);
        eventBus.post("3");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        AsyncEventBus asyncEventBus = new AsyncEventBus("Multiple-Async-EventBus", executor);
        asyncEventBus.register(listener);
        asyncEventBus.post(4);
        executor.shutdown();
    }

    @Subscribe
    public void listenNumber(Number number) {
        System.out.println("Listen number: " + number);
    }

    @Subscribe
    public void listenInteger(Integer number) {
        System.out.println("Listen integer: " + number);
    }

    @Subscribe
    public void listenLong(Long number) {
        System.out.println("Listen long: " + number);
    }

}
