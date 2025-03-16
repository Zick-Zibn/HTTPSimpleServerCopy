package ru.ilya.http.server.domain;

import java.util.Map;

public class Response<T> {

    private HTTPResponseStatusCode responseCode;
    private Map<String, String> headers;
    private T body;

    public Response() {
    }

    public Response(HTTPResponseStatusCode responseCode, Map<String, String> headers, T body) {
        this.responseCode = responseCode;
        this.headers = headers;
        this.body = body;
    }

    public Response(ResponseBuilder<T> builder) {
        this.responseCode = builder.responseCode;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public HTTPResponseStatusCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(HTTPResponseStatusCode responseCode) {
        this.responseCode = responseCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public static class ResponseBuilder<T> {

        private HTTPResponseStatusCode responseCode;
        private Map<String, String> headers;
        private T body;

        public ResponseBuilder(HTTPResponseStatusCode responseCode, Map<String, String> headers, T body) {
            this.responseCode = responseCode;
            this.headers = headers;
            this.body = body;
        }

        public Response<T> build() {
            return new Response<T>(this);
        }
    }
}
