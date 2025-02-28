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

public class RegisterPageHandler implements HttpHandler {

    final String FORM_PAGE = "registerPage.thtml";
    private Logger logger;
    private DisplayLogic displayLogic;
    private UserService userService;

    public RegisterPageHandler(Logger log, DisplayLogic dl, UserService userService) {
        logger = log;
        displayLogic = dl;
        this.userService = userService;  // ChatGPT told me to do this

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("RegisterPageHandler called");

        // dataModel will hold the data to be used in the template
        Map<String, Object> dataModel = new HashMap<String, Object>();

        Map<String, String> dataFromWebForm = displayLogic.parseResponse(exchange);

        // Extract values from the form
        String username = dataFromWebForm.get("username");
        String password = dataFromWebForm.get("password");
        String confirmPass = dataFromWebForm.get("confirmPassword");  // Make sure your form has a confirmPassword field
        String birthdayStr = dataFromWebForm.get("birthday");

        int age = calculateAge(birthdayStr);  // Implement a method to calculate age from the birthday

        // help from chatgpt for this if-statement
        // If the form was submitted, try to register the user
        // if (username != null && password != null && confirmPass != null) {
        //     boolean registrationSuccess = userService.registerUser(username, password, confirmPass, age);

        //     // Add appropriate messages to the data model based on registration outcome
        //     if (registrationSuccess) {
        //         dataModel.put("message", "Registration successful!");
        //     } else {
        //         dataModel.put("message", "Registration failed. Please check your logs.");
        //     }
        // }

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            logger.info("Register post detected");
            if (username != null && password != null && confirmPass != null) {
                boolean registrationSuccess = userService.registerUser(username, password, confirmPass, age);
                if (registrationSuccess) {
                    dataModel.put("message", "Registration successful!");
                    addUserCookie(exchange, username);
                } else {
                    dataModel.put("message", "Registration failed. Please check your logs.");
                }
            }
        }
        else{
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

    private void addUserCookie(HttpExchange exchange, String username) {
        String cookieHeader = "username=" + username;
        exchange.getResponseHeaders().add("Set-Cookie", cookieHeader);
    }

    private int calculateAge(String birthdayStr) {
        // Parse the birthday string and calculate age (simplified for brevity)
        // You can use a library like java.time.LocalDate to handle the date and calculate the age
        return 25;  // Placeholder, replace with actual calculation logic
    }
}
