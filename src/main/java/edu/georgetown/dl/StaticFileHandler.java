package edu.georgetown.dl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticFileHandler implements HttpHandler {

    private final String baseDir;

    public StaticFileHandler(String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestedFile = exchange.getRequestURI().getPath().replace("/uploads/", "");
        Path filePath = Paths.get(baseDir, requestedFile);

        if (Files.exists(filePath)) {
            String contentType = Files.probeContentType(filePath);
            exchange.getResponseHeaders().set("Content-Type", contentType != null ? contentType : "application/octet-stream");
            exchange.sendResponseHeaders(200, Files.size(filePath));
            OutputStream os = exchange.getResponseBody();
            Files.copy(filePath, os);
            os.close();
        } else {
            exchange.sendResponseHeaders(404, -1);
        }
    }
}