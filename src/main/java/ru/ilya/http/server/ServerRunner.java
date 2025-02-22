package ru.ilya.http.server;

import java.io.IOException;

public class ServerRunner {

    public static void main(String[] args) throws IOException {
        new SimpleServer(args).runServer();
    }
}
