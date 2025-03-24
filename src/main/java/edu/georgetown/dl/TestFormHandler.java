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


        // Vector<String> usernameVector = new Vector<>();

        // for (int i = 0; i < userService.getUsers().size(); i++){
        //         usernameVector.add(userService.getUsers().get(i).getUsername());
        // }

        dataModel.put("usernames", "usernameVector");

        
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
