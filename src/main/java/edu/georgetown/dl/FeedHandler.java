package edu.georgetown.dl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.georgetown.bll.ChirpService;
import edu.georgetown.bll.user.UserService;
import edu.georgetown.dao.Chirp;
import edu.georgetown.dao.Chirper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

public class FeedHandler implements HttpHandler {

    final String FORM_PAGE = "feedPage.thtml";
    private Logger logger;
    private DisplayLogic displayLogic;
    private UserService userService;
    private ChirpService chirpService;

    public FeedHandler(Logger log, DisplayLogic dl, UserService userService, ChirpService chirpService) {
        logger = log;
        displayLogic = dl;
        this.userService = userService;
        this.chirpService = chirpService;
    }

    private String extractContentDisposition(String part) {
        int cdIndex = part.indexOf("Content-Disposition");
        int endLine = part.indexOf("\r\n", cdIndex);
        return part.substring(cdIndex, endLine);
    }
    
    private String extractFileName(String contentDisposition) {
        String[] segments = contentDisposition.split(";");
        for (String seg : segments) {
            if (seg.trim().startsWith("filename=")) {
                return seg.split("=")[1].replace("\"", "").trim();
            }
        }
        return "unknown";
    }
    
    private String getFileExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return (dot == -1) ? "" : fileName.substring(dot + 1).toLowerCase();
    }
    
    private boolean isAllowedExtension(String ext) {
        return List.of("png", "jpg", "jpeg", "gif", "webp").contains(ext);
    }
    

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("FeedHandler called");
        Map<String, Object> dataModel = new HashMap<>();
        try{
            Map<String, String> cookies = displayLogic.getCookies(exchange);
            String username = cookies.get("username");
            Chirper currentUser;
            Map<String, String> queryParams = displayLogic.parseRequest(exchange);
            String searchTerm = queryParams.get("searchTerm");

            if (username == null) {
                dataModel.put("message", "No user logged in. Please log in first.");
                dataModel.put("chirps", List.of());
            } else {
                dataModel.put("username", username);
                currentUser = userService.getUserByUsername(username);
                dataModel.put("currentUser", currentUser);
                dataModel.put("message", "Welcome to your feed, " + username + "!");
            }
            
            // follow someone
            String userToFollow = queryParams.get("follow");
            if (userToFollow != null)
            {
                userService.followUser(username, userToFollow);
            }
            
            // chirp posting
                if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    String boundary = getBoundary(exchange.getRequestHeaders().getFirst("Content-Type"));
                    if (boundary != null) {
                        Map<String, String> fields = new HashMap<>();
                        String imageFileName = null;

                        InputStream is = exchange.getRequestBody();
                        byte[] bodyBytes = is.readAllBytes();
                        String body = new String(bodyBytes, "ISO-8859-1");

                        String[] parts = body.split("--" + boundary);
                        for (String part : parts) {
                            if (part.contains("Content-Disposition")) {
                                if (part.contains("filename=")) {
                                    // File upload
                                    int start = part.indexOf("\r\n\r\n") + 4;
                                    int end = part.lastIndexOf("\r\n");
                                    byte[] fileBytes = Arrays.copyOfRange(bodyBytes, body.indexOf(part) + start, body.indexOf(part) + end);
                                    
                                    String disposition = extractContentDisposition(part);
                                    String originalFileName = extractFileName(disposition);
                                    String extension = getFileExtension(originalFileName);
                                    
                                    if (isAllowedExtension(extension)) {
                                        String fileName = UUID.randomUUID() + "." + extension;
                                        File uploadDir = new File("uploaded_images");
                                        if (!uploadDir.exists()) uploadDir.mkdir();
                                        Path filePath = uploadDir.toPath().resolve(fileName);
                                        Files.write(filePath, fileBytes);
                                        imageFileName = fileName;
                                    } else {
                                        logger.warning("Unsupported file type: " + extension);
                                    }                                
                                } else if (part.contains("name=\"chirpMessage\"")) {
                                    int start = part.indexOf("\r\n\r\n") + 4;
                                    int end = part.lastIndexOf("\r\n");
                                    String message = part.substring(start, end).trim();
                                    fields.put("chirpMessage", message);
                                }
                            }
                        }

                        String chirpMessage = fields.get("chirpMessage");
                        if (chirpMessage != null && !chirpMessage.isEmpty()) {
                            chirpService.addChirp(username, chirpMessage, imageFileName); // You'll need to update ChirpService/Chirp
                            exchange.getResponseHeaders().set("Location", "/feedPage/");
                            exchange.sendResponseHeaders(302, -1);
                            return;
                        }
                    }
                }

                // Handle post display or search
                if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                    List<Chirp> searchResults = searchTerm.startsWith("#")
                            ? chirpService.searchByHashtag(searchTerm.substring(1))
                            : chirpService.searchByUsername(searchTerm);
                    dataModel.put("chirps", searchResults);
                    dataModel.put("searchTerm", searchTerm);
                } else {
                    currentUser = userService.getUserByUsername(username);
                    List<Chirper> following = currentUser.getFollowing();
                    dataModel.put("chirps", chirpService.getAllChirps(following));
                }

                // Display the follow list (users the logged-in user follows)
                Chirper user = userService.getUserByUsername(username);
                if (user != null) {
                    dataModel.put("followings", user.getFollowing()); // Add followings to the dataModel
                }
                

            if (!dataModel.containsKey("message")) {
                dataModel.put("message", "");
            }


            StringWriter sw = new StringWriter();
            displayLogic.parseTemplate(FORM_PAGE, dataModel, sw);
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, sw.getBuffer().length());
            OutputStream os = exchange.getResponseBody();
            os.write(sw.toString().getBytes());
            os.close();
        }
        catch (Exception e){
            logger.warning("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    

    private String getBoundary(String contentType) {
        if (contentType != null && contentType.contains("multipart/form-data")) {
            for (String param : contentType.split(";")) {
                param = param.trim();
                if (param.startsWith("boundary=")) {
                    return param.substring("boundary=".length());
                }
            }
        }
        return null;
    }
}

