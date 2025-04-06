package edu.georgetown.dl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("RegisterPageHandler called");

        Map<String, Object> dataModel = new HashMap<>();
        Map<String, String> formData = displayLogic.parseResponse(exchange);

        String username = formData.get("username");
        String password = formData.get("password");
        String confirmPass = formData.get("confirmPassword");
        String birthdayStr = formData.get("birthday");

        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            logger.info("Register POST detected");

            int age = calculateAge(birthdayStr);

            if (age < 18) {
                dataModel.put("message", "Registration failed: You must be 18 or older.");
            } else if (username != null && password != null && confirmPass != null) {
                boolean registrationSuccess = userService.registerUser(username, password, confirmPass, age);

                if (registrationSuccess) {
                    dataModel.put("message", "Registration successful!");
                    displayLogic.addCookie(exchange, "username", username);
                } else {
                    dataModel.put("message", "Registration failed. Please check your inputs.");
                }
            } else {
                dataModel.put("message", "Missing form data.");
            }
        } else {
            dataModel.put("message", " ");
        }

        StringWriter sw = new StringWriter();
        displayLogic.parseTemplate(FORM_PAGE, dataModel, sw);

        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, sw.getBuffer().length());

        OutputStream os = exchange.getResponseBody();
        os.write(sw.toString().getBytes());
        os.close();
    }

    private int calculateAge(String birthdayStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthdayStr, formatter);
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (Exception e) {
            logger.warning("Invalid birthday format: " + birthdayStr);
            return 0;
        }
    }
}
