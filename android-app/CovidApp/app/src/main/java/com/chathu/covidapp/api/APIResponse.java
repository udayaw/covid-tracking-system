package com.chathu.covidapp.api;

public class APIResponse {
    public Object getBody() {
        return body;
    }

    public APIResponse(APIResponseStatus status, Object body){
        this.status = status;
        this.body = body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public APIResponseStatus getStatus() {
        return status;
    }

    public void setStatus(APIResponseStatus status) {
        this.status = status;
    }

    public enum APIResponseStatus{
        SUCCESS,
        FAIL
    }
    private Object body;
    private APIResponseStatus status;

}
