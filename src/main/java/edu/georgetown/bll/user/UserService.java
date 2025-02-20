package edu.georgetown.bll.user;


import java.util.Vector;
import java.util.logging.Logger;

import edu.georgetown.dao.*;

public class UserService {

    private static Logger logger;
    private Vector<Chirper> users;

    public UserService(Logger log) {
        logger = log;
        logger.info("UserService started");
        users = new Vector<>();
    }

    public Vector<Chirper> getUsers() {
        // not implemented; you'll need to change this
        return users;
    }

    public void registerUser(Logger newUser){
        
    }
    // methods you'll probably want to add:
    //   registerUser
    //   loginUser
    //   etc.

}
