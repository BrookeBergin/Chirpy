package edu.georgetown.dl;

import java.util.Vector;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.georgetown.bll.ChirpService;
import edu.georgetown.bll.user.UserService;
import edu.georgetown.dao.Chirp;

public class FeedHandler implements HttpHandler {

    final String FORM_PAGE = "feedPage.thtml";
    private Logger logger;
    private DisplayLogic displayLogic;
    private UserService userService;
    private ChirpService chirpService;

    // public FeedHandler(Logger log, DisplayLogic dl) {
    //     logger = log;
    //     displayLogic = dl;
    // }

    public FeedHandler(Logger log, DisplayLogic dl, UserService userService, ChirpService chirpService) {
        logger = log;
        displayLogic = dl;
        this.userService = userService;
        this.chirpService = chirpService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("FeedHandler called");

        // dataModel will hold the data to be used in the template
        Map<String, Object> dataModel = new HashMap<String, Object>();
        

        // Vector<String> usernameVector = new Vector<>();

        // for (int i = 0; i < userService.getUsers().size(); i++){
        //         usernameVector.add(userService.getUsers().get(i).getUsername());
        // }
        // Extract values from the form
        // String username = dataFromWebForm.get("username");
        // String password = dataFromWebForm.get("password");


        //String username = "test";
        //String password = "password";

        /* If the form was submitted, attempt to log in
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            logger.info("Login POST detected");
            if (username != null && password != null) {
                boolean loginSuccess = userService.loginUser(username, password);

                if (loginSuccess) {
                    dataModel.put("message", "Login successful!");
                    //addUserCookie(exchange, username);  // Optionally set a user cookie
                } else {
                    dataModel.put("message", "Login failed. Please check your credentials.");
                }
            }
        } else {
            dataModel.put("message", " ");
        }
        */
        //Retrieve the logged-in username from cookies
        Map<String, String> cookies = displayLogic.getCookies(exchange);
        String username = cookies.get("username");
        if(username == null){
            dataModel.put("message", "No user logged in. Please log in first.");
            dataModel.put("chirps", List.of());
        }
        else{
            dataModel.put("message", "Welcome to your feed, " + username + "!");
            //If a POST request is recieved, assume a new chirp is being submitted
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Handle chirp submission
                Map<String, String> formData = displayLogic.parseResponse(exchange);
                String chirpMessage = formData.get("chirpMessage");
    
                if (chirpMessage != null && !chirpMessage.trim().isEmpty()) {
                    chirpService.addChirp(username, chirpMessage);
                    exchange.getResponseHeaders().set("Location", "/feedPage/");
                    exchange.sendResponseHeaders(302, -1);
                    return;
                } else {
                    logger.info("POST received with empty chirpMessage; treating as GET.");
                }
            }

         // Handle search via GET
            Map<String, String> queryParams = displayLogic.parseRequest(exchange);
            String searchTerm = queryParams.get("searchTerm");

            logger.info("QUERY PARAMS: " + queryParams);
            logger.info("SEARCH TERM: " + searchTerm);
            
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                logger.info("Search request: " + searchTerm);
                List<Chirp> searchResults;

                if (searchTerm.startsWith("#")) {
                    searchResults = chirpService.searchByHashtag(searchTerm.substring(1));
                } else {
                    searchResults = chirpService.searchByUsername(searchTerm);
                }

                dataModel.put("chirps", searchResults);
                dataModel.put("searchTerm", searchTerm);
            } else {
                // Default feed
                dataModel.put("chirps", chirpService.getAllChirps());
            }
        }


        // Ensure message is always set
        if (!dataModel.containsKey("message")) {
            dataModel.put("message", "");
        }
        
        // sw will hold the output of parsing the template
        StringWriter sw = new StringWriter();

        // now we call the display method to parse the template and write the output
        displayLogic.parseTemplate(FORM_PAGE, dataModel, sw);

        // set the type of content (in this case, we're sending back HTML)
        exchange.getResponseHeaders().set("Content-Type", "text/html");

        // send the HTTP headers
        exchange.sendResponseHeaders(200, (sw.getBuffer().length()));

        // finally, write the actual response (the contents of the template)
        OutputStream os = exchange.getResponseBody();
        os.write(sw.toString().getBytes());
        os.close();
    }
    
}

