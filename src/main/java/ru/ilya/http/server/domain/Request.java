package ru.ilya.http.server.domain;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private RequestType requestType;

    private String filename;

    private String httpVersion;

    private Map<String, String> headers = new HashMap<>();

    private String body;

    public Request() {
    }

    public Request(RequestType requestType, String filename, String httpVersion, Map<String, String> headers, String body) {
        this.requestType = requestType;
        this.filename = filename;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
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

    public String getFilename() {
        return filename = filename.substring(1);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }
}
