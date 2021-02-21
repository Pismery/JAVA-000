package org.pismery.javacourse.mq.core;

import java.util.Map;

public class PisMessage<T> {
    private Map<String, Object> header;
    private T body;

    public PisMessage(Map<String, Object> header, T body) {
        this.header = header;
        this.body = body;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
