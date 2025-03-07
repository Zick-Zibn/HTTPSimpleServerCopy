package ru.ilya.http.server.domain;

import java.util.Map;

public class Response {

    private HTTPResponseStatusCode responseCode;
    private Map<String, String> headers;
    private String body;

    public Response() {
    }

    public Response(HTTPResponseStatusCode responseCode, Map<String, String> headers, String body) {
        this.responseCode = responseCode;
        this.headers = headers;
        this.body = body;
    }

    public Response(ResponseBuilder builder) {
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static class ResponseBuilder {

        private HTTPResponseStatusCode responseCode;
        private Map<String, String> headers;
        private String body;

        public ResponseBuilder(HTTPResponseStatusCode responseCode, Map<String, String> headers, String body) {
            this.responseCode = responseCode;
            this.headers = headers;
            this.body = body;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
