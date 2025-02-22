package ru.ilya.http.server.domain;

import java.util.Map;

public class Response {

    private int responseCode;

    private Map<String, String> headers;

    private String body;

    public Response() {
    }
    public Response(int responseCode, Map<String, String> headers, String body) {
        this.responseCode = responseCode;
        this.headers = headers;
        this.body = body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
