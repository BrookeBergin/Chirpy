package edu.georgetown.dl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * class LogoutHandler handles the log out of a user 
 * contains logger and display logic
 * 
 * @author Anura Sharma, Brooke Bergin, Diane Cho, Kamryn Lee-Caracci
 * @version 1.0
 * @since 1.0
 */
public class LogoutHandler implements HttpHandler{
    private Logger logger;
    private DisplayLogic displayLogic;

    /** 
     * constructor for class
     * @param logger the logger
     * @param displayLogic the display logic
     */
    public LogoutHandler(Logger logger, DisplayLogic displayLogic){
        this.logger = logger;
        this.displayLogic = displayLogic;
    }

    /** 
     * handle, handles the log out
     * @param exchange the http exchange
     * @throws IOException for server errors
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException{
        logger.info("LogoutHandler called");

        //Remove the session cookie by setting its max-age to 0;
        displayLogic.removeCookie(exchange, "username");

        //Prepare a simple response page.
        String response = "<html><vody><h1>You have been logged out.</h1>" + "<a href=\"/\">Return to Home</a/</body></html>";

        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}