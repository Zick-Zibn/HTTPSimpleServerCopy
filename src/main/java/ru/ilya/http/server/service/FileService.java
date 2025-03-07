package ru.ilya.http.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    private final Path rootPath;

    public FileService(Path rootPath) {
        this.rootPath = rootPath;
    }

    public boolean isFileExists(Path filePath) {
        return Files.exists(rootPath.resolve(filePath));
    }

    public InputStream readFile(Path fileName) throws IOException {
        return Files.newInputStream(Path.of(rootPath.resolve(fileName).toUri()));
    }

    public static String getFileExtension(Path filePath) {
        String fileName = filePath.toFile().getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public static String getMIMEType(Path filePath) throws IOException {

        return Files.probeContentType(filePath);

    }
}
