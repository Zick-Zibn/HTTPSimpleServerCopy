package ru.ilya.http.server;

import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.domain.Response;
import ru.ilya.http.server.service.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class SimpleServer {

    private final Configuration configuration;

    private final FileService fileService;

    public SimpleServer(String[] args) {
        this.configuration = new Configuration(args);
        this.fileService = new FileService(configuration.getRootFolder());
    }

    public void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(configuration.getPort())) {
            System.out.printf("Server started at port %d%n", configuration.getPort());

            while (true) {
                try {
                    Socket socket = serverSocket.accept();

                    System.out.println("New connection accepted");
                    ClientSocketService clientSocketService = new ClientSocketService(
                            socket,
                            new RequestParser(),
                            new ResponseSerializer());

                    Request request = clientSocketService.readRequest();
                    if (request != null) {
                        Path filePath = Paths.get(request.getFilename());

                        Response response = this.initResponse(filePath, fileService);
                        clientSocketService.writeResponse(response);
                        socket.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Response initResponse(Path filePathRequest, FileService fileService) throws IOException {

        Path filePath;
        HashMap<String, String> header = new HashMap<>();
        Response response;
        int responseCode = 0;
        String responseBody;

        if (fileService.isFileExists(filePathRequest)) {
            if (filePathRequest.equals(Path.of(""))) {
                filePath = Path.of(Configuration.INDEX_FILE);
            } else {
                filePath = filePathRequest;
            }
            responseCode = 200;
        } else {
            filePath = Path.of(Configuration.FILE_NOT_FOUND_NAME);
            responseCode = 404;
        }
        header.put("Content-Type:", " text/html; charset=utf-8");
        try (InputStream inputStream = fileService.readFile(filePath)) {
            responseBody = new String(inputStream.readAllBytes());
        }
        response = new Response(responseCode, header, responseBody);

        return response;
        // TODO complete request handling
        // TODO Response response = new Response(200, )
    }
}


