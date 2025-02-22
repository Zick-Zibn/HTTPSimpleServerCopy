package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.domain.Response;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


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

    public Request readRequest() throws IOException {
        Request request = null;
        StringBuilder textBuilder = new StringBuilder();
        int secondsTimeOut = 10;
        LocalTime timeEnd = LocalTime.now().plusSeconds(secondsTimeOut);

        while (!input.ready()){
            if (LocalTime.now().isAfter(timeEnd)){
                return request;
            }
        } // TODO implement here timeout

        while (input.ready()) {
            textBuilder.append(input.readLine()).append("\r\n");
        }
        if (!textBuilder.isEmpty()){
            request = requestParser.parse(textBuilder.toString());
        }
        return request;
        /*
        return requestParser.parse(textBuilder.toString());
        */
    }

    public void writeResponse(Response response) {
        String rawResponse = responseSerializer.serialize(response);
        output.print(rawResponse); // what about extra new line at the end. May be just print()
        output.flush();
    }
}
