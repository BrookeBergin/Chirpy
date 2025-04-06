package edu.georgetown.dl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import edu.georgetown.bll.user.UserService;

/**
 * class TestFormHandler is the class that handles log in info
 * (the login form is referred to as the TestForm in the Chirpy application)
 * 
 * contains the logger, display logic, and user service
 * 
 * @author Anura Sharma, Brooke Bergin, Diane Cho, Kamryn Lee-Caracci
 * @version 1.0
 * @since 1.0
 */
public class TestFormHandler implements HttpHandler {

    final String FORM_PAGE = "formtest.thtml";
    private Logger logger;
    private DisplayLogic displayLogic;
    private UserService userService;

    /**
     * the constructor for the class
     * @param log the logger
     * @param dl the display logic
     * @param userService the user service
     */
    public TestFormHandler(Logger log, DisplayLogic dl, UserService userService) {
        logger = log;
        displayLogic = dl;
        this.userService = userService;
    }

    /**
     * the handle function handles all login functionality
     * 
     * logs in and redirects to feed page
     * sends message to datamodel to be displayed in html
     * 
     * @param exchange the http exchange used
     * @throws IOException for server or connection errors
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("TestFormHandler called");

        // dataModel will hold the data to be used in the template
        Map<String, Object> dataModel = new HashMap<String, Object>();

        
        // detect post request, proceed with login
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            logger.info("Login POST detected");

            //parse the form data from the request body
            Map<String,String> formData = displayLogic.parseResponse(exchange);
            String username = formData.get("username");
            String password = formData.get("password");

            if (username != null && password != null) {
                boolean loginSuccess = userService.loginUser(username, password);

                if (loginSuccess) {
                    dataModel.put("message", "Login successful!");
                    displayLogic.addCookie(exchange, "username", username);
                    // redirect to feedpage!
                    exchange.getResponseHeaders().set("Location", "/feedPage/");
                    exchange.sendResponseHeaders(302, -1);
                    return;
                    
                } else {
                    dataModel.put("errorMsg", "Login failed. Please check your credentials.");
                }
            }
            else{
                dataModel.put("errorMsg", "Missing username or password.");
            }
        } else {
            dataModel.put("errorMsg", " ");
        }
        Map<String, String> cookies = displayLogic.getCookies(exchange);
        String user = cookies.get("username");
        if(user==null){
            dataModel.put("message", "Not logged in. Please log in first.");
        }
        else{
            dataModel.put("message", "Welcome to your feed, " + user + "!");
            dataModel.put("username", user);
            logger.info("added user to datamodel");
        }


        // Ensure message is always set
        if (!dataModel.containsKey("message")) {
            dataModel.put("message", "");
        }
        logger.info("message is `" + dataModel.get("message") + "`");
        logger.info("user is `" + dataModel.get("username") + "`");
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
