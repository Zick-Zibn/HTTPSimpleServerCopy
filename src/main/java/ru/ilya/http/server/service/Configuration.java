package ru.ilya.http.server.service;

import com.beust.jcommander.Parameter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.beust.jcommander.*;

public class Configuration {

    @Parameter(names = {"-p", "--port"}, description = "Номер порта")
    private Integer port;

    @Parameter(names = {"-f", "--folder"}, description = "Каталог файлов")
    private String rootFolder;

    public static final String FILE_NOT_FOUND_NAME = "notFound.txt";
    public static final String INDEX_FILE = "index.txt";

    public Configuration(String[] args) {
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);

        if (Files.notExists(Paths.get(this.rootFolder))) {
            throw new IllegalArgumentException("Каталог не существует");
        }
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException(
                String.format("Значение порта %d недопустимо, используйте значение в диапазоне от 1 до 65535", port));
        }
    }

    public int getPort() {
        return port;
    }

    public Path getRootFolder() {
        return Paths.get(rootFolder);
    }
}
