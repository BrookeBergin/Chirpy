/**
 * Chirpy -- a really basic social networking site
 * 
 */

package edu.georgetown;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.sun.net.httpserver.HttpServer;

import edu.georgetown.bll.ChirpService;
import edu.georgetown.bll.user.UserService;
import edu.georgetown.dl.DefaultPageHandler;
import edu.georgetown.dl.DisplayLogic;
import edu.georgetown.dl.ListCookiesHandler;
import edu.georgetown.dl.RegisterPageHandler;
import edu.georgetown.dl.StaticFileHandler;
import edu.georgetown.dl.FeedHandler;
import edu.georgetown.dl.TestFormHandler;
import edu.georgetown.dl.LogoutHandler;

/**
    * Chipry class - main logic for the Chirpy platform.
    *
    * @author Anura Sharma, Brooke Bergin, Diane Cho, Kamryn Lee-Caracci
    * @version 1.0
    * @since 1.0
*/
public class Chirpy {

    final static int PORT = 8080;

    private Logger logger;
    private DisplayLogic displayLogic;
    private UserService userService;

    /**
     * Constructor for Chirpy, an instance of the class Chirpy.
     * 
     * This initializes the beginnings of the Chirpy service including the logger,
     * display logic, and user service.
     * 
     */
    public Chirpy() {
        /* 
         * A Logger is a thing that records "log" messages.  This is better
         * than using System.out.println() because you can control which
         * messages are logged.  For example, you can log only "warning"
         * messages, or you can log all messages.
         * 
         * We'll create one logger, call it `logger`, and then pass this
         * logger to our classes.  This way, all of our classes will log
         * to the same place.
         */
        logger = Logger.getLogger("MyLogger");
        try {
            FileHandler fileHandler = new FileHandler("/tmp/log.txt");
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false); // Remove default handlers
        // Set desired log level (e.g., Level.INFO, Level.WARNING, etc.)
        logger.setLevel(Level.ALL); 

        try {
            displayLogic = new DisplayLogic(logger);
        } catch (IOException e) {
            logger.warning("failed to initialize display logic: " + e.getMessage());
            System.exit(1);
        }
        userService = new UserService(logger);
        
        logger.info("Starting chirpy web service");

    }

    /**
     * Start the web service.
     * 
     * @throws IOExcepction for input/output errors (for example, an error
     * connecting to the server.)
     */
    private void startService() {
  
        try {
            // initialize the web server
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);

            // each of these "contexts" below indicates a URL path that will be handled by
            // the service. The top-level path is "/", and that should be listed last.
            server.createContext("/formtest/", new TestFormHandler(logger, displayLogic, userService));
            server.createContext("/registerPage/", new RegisterPageHandler(logger, displayLogic, userService));
            server.createContext("/listcookies/", new ListCookiesHandler(logger, displayLogic, userService));
            //Create a new ChirpService Instance and pass it tp FeedHandler
            ChirpService chirpService = new ChirpService();
            server.createContext("/feedPage/", new FeedHandler(logger, displayLogic, userService, chirpService));
            server.createContext("/logout/", new LogoutHandler(logger, displayLogic));
            server.createContext("/", new DefaultPageHandler(logger, displayLogic));
            server.createContext("/uploads/", new StaticFileHandler("uploaded_images"));

            // you will need to add to the above list to add new functionality to the web
            // service.  Just make sure that the handler for "/" is listed last.

            server.setExecutor(null); // Use the default executor

            // this next line effectively starts the web service and waits for requests. The
            // above "contexts" (created via `server.createContext`) will be used to handle
            // the requests.
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        logger.info("Server started on port " + PORT);
    }

    /**
     * main - the entry point for Chirpy
     * 
     * @param args
     */
    public static void main(String[] args) {

        Chirpy ws = new Chirpy();
        ws.startService();
        
    }

}
