package edu.georgetown.dl;

import java.util.Vector;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import edu.georgetown.bll.user.UserService;

public class TestFormHandler implements HttpHandler {

    final String FORM_PAGE = "formtest.thtml";
    private Logger logger;
    private DisplayLogic displayLogic;
    private UserService userService;

    // public TestFormHandler(Logger log, DisplayLogic dl) {
    //     logger = log;
    //     displayLogic = dl;
    // }

    public TestFormHandler(Logger log, DisplayLogic dl, UserService userService) {
        logger = log;
        displayLogic = dl;
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("TestFormHandler called");

        // dataModel will hold the data to be used in the template
        Map<String, Object> dataModel = new HashMap<String, Object>();

        //String username = "test";
        //String password = "password";

        // If the form was submitted, attempt to log in
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
                    //addUserCookie(exchange, username);  // Optionally set a user cookie
                    //Set a cookie and then redirect to the FeedHandler page
                    displayLogic.addCookie(exchange, "username", username);
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
        }


        // Ensure message is always set
        if (!dataModel.containsKey("message")) {
            dataModel.put("message", "");
        }
        logger.info("message is `" + dataModel.get("message") + "`");
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
