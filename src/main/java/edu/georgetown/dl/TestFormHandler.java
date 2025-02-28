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

public class TestFormHandler implements HttpHandler {

    final String FORM_PAGE = "formtest.thtml";
    private Logger logger;
    private DisplayLogic displayLogic;

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

        Map<String, String> dataFromWebForm = displayLogic.parseResponse(exchange);

        // // if the web form contained a username, add it to the data model
        // if (dataFromWebForm.containsKey("username")) {
        //     dataModel.put("username", dataFromWebForm.get("username"));
        // }
        // Extract values from the form
        String username = dataFromWebForm.get("username");
        String password = dataFromWebForm.get("password");

        // If the form was submitted, attempt to log in
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            logger.info("Login POST detected");
            if (username != null && password != null) {
                boolean loginSuccess = userService.loginUser(username, password);

                if (loginSuccess) {
                    dataModel.put("message", "Login successful!");
                    addUserCookie(exchange, username);  // Optionally set a user cookie
                } else {
                    dataModel.put("message", "Login failed. Please check your credentials.");
                }
            }
        } else {
            dataModel.put("message", " ");
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
        
        // if we have a username, set a cookie with the username.
        if (dataFromWebForm.containsKey("username")) {
            displayLogic.addCookie(exchange, "username", dataFromWebForm.get("username"));
        }

        // send the HTTP headers
        exchange.sendResponseHeaders(200, (sw.getBuffer().length()));

        // finally, write the actual response (the contents of the template)
        OutputStream os = exchange.getResponseBody();
        os.write(sw.toString().getBytes());
        os.close();
    }
}
