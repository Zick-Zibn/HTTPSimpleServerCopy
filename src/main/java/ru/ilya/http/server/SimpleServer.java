package ru.ilya.http.server;

import ru.ilya.http.server.domain.HTTPResponseStatusCode;
import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.domain.Response;
import ru.ilya.http.server.service.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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

                    clientSocketService.waitForRequest(configuration.getTimeOut()); // move timeout value to Configuration //ok
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

    private Response initResponse(Path filePathRequest) throws IOException, URISyntaxException {
        Path filePath;
        HashMap<String, String> header = new HashMap<>();
        HTTPResponseStatusCode statusCode;
        byte[] responseBody;

        if (fileService.isFileExists(filePathRequest)) {
            if (filePathRequest.equals(Path.of(""))) {
                filePath = Path.of(Configuration.INDEX_FILE);
            } else {
                filePath = filePathRequest;
            }
            statusCode = HTTPResponseStatusCode.RESPONSE_CODE_200; // TODO add constants for response codes // completed
        } else {
            // TODO here read the file from application resources // completed
            filePath = Path.of(Objects.requireNonNull(SimpleServer.class.getResource("/not_found.html")).toURI());
            statusCode = HTTPResponseStatusCode.RESPONSE_CODE_404;
        }
        // TODO handle other media type. For example for pictures (jpeg, bmp, ico, etc...)

        StringBuilder mimeType = new StringBuilder();
        mimeType.append(FileService.getMIMEType(filePath));

        if (mimeType.toString().contains("text")) {
            header.put("Content-Type:", String.format("%s; charset=%s", mimeType, StandardCharsets.UTF_8));
        } else {
            header.put("Accept-Ranges:", "bytes");
            header.put("Content-Type:", mimeType.toString());
        }

        try (InputStream inputStream = fileService.readFile(filePath)) {
            responseBody = inputStream.readAllBytes();
            ;
            header.put("Content-Length:", String.valueOf(responseBody.length));
        }

        // TODO implement Builder pattern for Response // completed
        return new Response.ResponseBuilder(statusCode, header, responseBody).build();
    }
}
