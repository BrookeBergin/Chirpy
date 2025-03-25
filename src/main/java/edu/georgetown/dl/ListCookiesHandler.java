package edu.georgetown.dl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ListCookiesHandler implements HttpHandler {

    final String COOKIELIST_PAGE = "showcookies.thtml";
    private Logger logger;
    private DisplayLogic displayLogic;


    public ListCookiesHandler(Logger log, DisplayLogic dl) {
        logger = log;
        displayLogic = dl;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("ListCookiesHandler called");
    
        // dataModel will hold the data to be used in the template
        Map<String, Object> dataModel = new HashMap<String, Object>();
    
        try { // disclosure: ChatGPT helped me with the syntax of the try/catch (31-46)
            // grab all of the cookies that have been set
            Map<String, String> cookies = displayLogic.getCookies(exchange);
    
            if (cookies != null) {
                dataModel.put("cookienames", cookies.keySet());
                dataModel.put("cookievalues", cookies.values());
            } else {
                // if cookies is null, just fill the dataModel with a date as fallback
                dataModel.put("date", new Date().toString());
            }
        } catch (Exception e) {
            // If an error occurs (e.g., null cookies or any other error), fallback to a default value
            logger.warning("Error retrieving cookies: " + e.getMessage());
            dataModel.put("date", new Date().toString());  // Use current date if error occurs
        }
    
        // sw will hold the output of parsing the template
        StringWriter sw = new StringWriter();
    
        // now we call the display method to parse the template and write the output
        displayLogic.parseTemplate(COOKIELIST_PAGE, dataModel, sw);
    
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
