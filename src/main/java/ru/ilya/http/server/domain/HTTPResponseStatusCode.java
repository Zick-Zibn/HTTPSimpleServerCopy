package ru.ilya.http.server.domain;

public enum HTTPResponseStatusCode {
    RESPONSE_CODE_200("200 Ok"),
    RESPONSE_CODE_404("404 Not Found");

    private final String name;

    HTTPResponseStatusCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
