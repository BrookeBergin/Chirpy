package edu.georgetown.dl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.georgetown.bll.user.UserService;
import edu.georgetown.dao.Chirper;

/**
 * class ListCookiesHandler is the handler for the showcookies.ththml page that lists
 * all the users currently registered for the Chirpy service
 * contains a logger, display logic, and user service
 * 
 * @author Anura Sharma, Brooke Bergin, Diane Cho, Kamryn Lee-Caracci
 * @version 1.0
 * @since 1.0
 */
public class ListCookiesHandler implements HttpHandler {

    final String COOKIELIST_PAGE = "showcookies.thtml";
    private Logger logger;
    private DisplayLogic displayLogic;
    private UserService userService;

    //private static final List<String> allUsernames = new ArrayList<>();
    Vector<Chirper> allUsernames;

    /**
     * constructor for class
     * @param log the logger
     * @param dl the display logic
     * @param userService the user service
     */
    public ListCookiesHandler(Logger log, DisplayLogic dl, UserService userService) {
        logger = log;
        displayLogic = dl;
        this.userService = userService;
    }

    /**
     * the handler (main function for this class)
     * 
     * does all functionality - retrieves list of users and passes it to html to be parsed as a list
     * 
     * @param exchange the http exchange
     * @throws IOException for server errors
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("ListCookiesHandler called");
    
        // dataModel will hold the data to be used in the template
        Map<String, Object> dataModel = new HashMap<String, Object>();
    
        try {
            Map<String, String> cookies = displayLogic.getCookies(exchange);

            if (cookies != null) {
                allUsernames = userService.getUsers();
                dataModel.put("allUsernames", allUsernames);
            } else {
                // if cookies is null, just fill the dataModel with a date as fallback
                dataModel.put("date", new Date().toString());
            }
        } catch (Exception e) {
            logger.warning("Error retrieving cookies: " + e.getMessage());
            dataModel.put("date", new Date().toString());  //current date
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
