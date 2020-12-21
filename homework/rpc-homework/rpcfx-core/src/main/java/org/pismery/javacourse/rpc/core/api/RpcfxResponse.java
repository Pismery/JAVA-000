package org.pismery.javacourse.rpc.core.api;

public class RpcfxResponse {

    private ApiBean result;

    private boolean status;

    private Exception exception;

    public ApiBean getResult() {
        return result;
    }

    public void setResult(ApiBean result) {
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
