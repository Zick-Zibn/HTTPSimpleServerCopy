package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Response;

import java.util.Map;

public class ResponseSerializer {

    // TODO handle all fields from Response class
    public String serialize(Response response) {
        StringBuilder stringResponse = new StringBuilder();
        stringResponse
                .append("HTTP/1.1 ")
                .append(this.strResponseCode(response.getResponseCode()))
                .append("\r\n");

        Map<String, String> mapHeaders = response.getHeaders();
        for (Map.Entry<String, String> pair : mapHeaders.entrySet()) {
            String headerLine = pair.getKey() + pair.getValue() + "\r\n"; // TODO add here space between key and value
            stringResponse.append(headerLine);
        }
        return stringResponse
                .append("\r\n")
                .append(response.getBody())
                .toString();
    }

    private String strResponseCode(int responseCode) {
        return switch (responseCode) {
            case 200 -> responseCode + " OK";
            case 404 -> responseCode + " Not Found";
            default -> throw new IllegalArgumentException(String.format(
                    "Пока код ответа %s не поддерживается, ожидайте в следующем релизе", responseCode));
        };
    }
}
