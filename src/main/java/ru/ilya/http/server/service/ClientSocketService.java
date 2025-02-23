package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.domain.Response;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;

public class ClientSocketService {

    private final RequestParser requestParser;
    private final ResponseSerializer responseSerializer;
    private final BufferedReader input;
    private final PrintWriter output;

    public ClientSocketService(
            Socket socket,
            RequestParser requestParser,
            ResponseSerializer responseSerializer
    ) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new PrintWriter(socket.getOutputStream());
        this.requestParser = requestParser;
        this.responseSerializer = responseSerializer;
    }

    public void waitForRequest(int secondsTimeOut) throws IOException {
        LocalTime timeEnd = LocalTime.now().plusSeconds(secondsTimeOut);
        while (!input.ready()) {
            // TODO use now(Clock clock) and provide Clock instance through the constructor
            if (LocalTime.now().isAfter(timeEnd)) {
                throw new IllegalStateException("Timeout with waiting for the first request");
            }
        }
    }

    public Request readRequest() throws IOException {
        Request request = null;
        StringBuilder textBuilder = new StringBuilder();
        while (input.ready()) {
            textBuilder.append(input.readLine()).append("\r\n");
        }
        if (!textBuilder.isEmpty()) {
            request = requestParser.parse(textBuilder.toString());
        }
        return request;
    }

    public void writeResponse(Response response) {
        String rawResponse = responseSerializer.serialize(response);
        output.print(rawResponse); // what about extra new line at the end. May be just print()
        output.flush();
    }
}
