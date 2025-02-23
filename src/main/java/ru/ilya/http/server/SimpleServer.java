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
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("New connection accepted");
                    ClientSocketService clientSocketService = new ClientSocketService(
                            socket,
                            new RequestParser(),
                            new ResponseSerializer());

                    clientSocketService.waitForRequest(10); // move timeout value to Configuration
                    Request request = clientSocketService.readRequest();
                    Path filePath = Paths.get(request.getFilename());

                    Response response = this.initResponse(filePath);
                    clientSocketService.writeResponse(response);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Response initResponse(Path filePathRequest) throws IOException {
        Path filePath;
        HashMap<String, String> header = new HashMap<>();
        int responseCode;
        String responseBody;

        if (fileService.isFileExists(filePathRequest)) {
            if (filePathRequest.equals(Path.of(""))) {
                filePath = Path.of(Configuration.INDEX_FILE);
            } else {
                filePath = filePathRequest;
            }
            responseCode = 200; // TODO add constants for response codes
        } else {
            filePath = Path.of(Configuration.FILE_NOT_FOUND_NAME); // TODO here read the file from application resources
            responseCode = 404; // TODO add constants for response codes
        }
        // TODO handle other media type. For example for pictures (jpeg, bmp, ico, etc...)
        header.put("Content-Type:", " text/html; charset=utf-8"); // TODO handle space in response serialization
        try (InputStream inputStream = fileService.readFile(filePath)) {
            responseBody = new String(inputStream.readAllBytes());
        }
        // TODO implement Builder pattern for Response
        return new Response(responseCode, header, responseBody);
    }
}
