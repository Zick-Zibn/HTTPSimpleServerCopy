package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.domain.RequestType;

import java.util.List;
import java.lang.*;

public class RequestParser {

    public Request parse(String rawRequest) {
        Request request = new Request();

//        int endOfHeadersIndex = rawRequest.indexOf("\r\n\r\n");
//        rawRequest.substring(endOfHeadersIndex);

        String[] headerBody = rawRequest.split("\r\n\r\n");
        List<String> stringHeaderList = headerBody[0].lines().toList();
        String firstLine = stringHeaderList.getFirst();
        String[] requestParams = firstLine.split(" ");

        request.setRequestType(RequestType.valueOf(requestParams[0]));
        request.setFilename(requestParams[1]);
        request.setHttpVersion(requestParams[2]);

        for (int i = 1; i < stringHeaderList.size(); i++) {
            String s = stringHeaderList.get(i);
            String[] arrString = s.split(": ");
            request.getHeaders().put(arrString[0], arrString[1]);
        }

        if (headerBody.length > 1) {
            request.setBody(headerBody[1]);
        }
        // TODO concat all lines from index i to save them into request.body

        return request;
    }
}
