package edu.georgetown.dl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * StaticFileHandler class handles all static files (images)
 * @author Anura Sharma, Brooke Bergin, Diane Cho, Kamryn Lee-Caracci
 * @version 1.0
 * @since 1.0
 */
public class StaticFileHandler implements HttpHandler {

    private final String baseDir;

    /**
     * constructor for class
     * @param baseDir the directory for the static file
     */
    public StaticFileHandler(String baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * handles the files:
     * gets the requested file from /uploads/
     * checks that file exists
     * sets the content type and sends to the output stream (the exchange)
     * 
     * @param exchange the http exchange
     * @throws IOException for server or retrieval errors
     */
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