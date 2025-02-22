package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Response;

import java.util.HashMap;
import java.util.Map;

public class ResponseSerializer {

    // TODO handle all fields from Response class
    public String serialize(Response response) {
        StringBuilder stringResponse = new StringBuilder();
        // TODO implement HTTP response string creation based on response object from parameter
        stringResponse.append("HTTP/1.1 ").append(this.strResponseCode(response.getResponseCode())).append("\r\n");

        Map<String, String> mapHeaders = response.getHeaders();
        for (Map.Entry<String, String> pair : mapHeaders.entrySet()) {
            String headerLine = pair.getKey() + pair.getValue() + "\r\n";
            stringResponse.append(headerLine);
        }
        stringResponse.append("\r\n");
        stringResponse.append(response.getBody());

        return stringResponse.toString();
    }

    private String strResponseCode(int responseCode) {
        String stringResponseCode;
        switch (responseCode){
            case 200:
                stringResponseCode = responseCode + " OK";
                break;
            case 404:
                stringResponseCode = responseCode + " Not Found";
                break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Пока код ответа %s не поддерживается, ожидайте в следующем релизе", responseCode));
        }
        return stringResponseCode;
    }
}
