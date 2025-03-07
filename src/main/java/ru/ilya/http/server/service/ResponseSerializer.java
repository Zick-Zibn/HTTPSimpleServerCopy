package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Response;
import java.util.Map;

public class ResponseSerializer {

    public String serialize(Response response) {
        StringBuilder stringResponse = new StringBuilder();
        Map<String, String> mapHeaders = response.getHeaders();

        stringResponse.append(String.format("HTTP/1.1 %s \r\n", response.getResponseCode().getName()));

        for (Map.Entry<String, String> pair : mapHeaders.entrySet()) {
            String headerLine = String.format("%s %s\r\n",pair.getKey(), pair.getValue()); // TODO add here space between key and value // completed
            stringResponse.append(headerLine);
        }

        return stringResponse
                .append("\r\n")
                .append(response.getBody())
                .toString();
    }
}
