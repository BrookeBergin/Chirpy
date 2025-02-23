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

    public String registerUser(String username, String password, String confirmPass){
        if(username == null || username.isEmpty()){
            logger.warning("Username cannot be empty.");
            return("Username cannot be empty");
        }
        
        for(Chirper user : users){
            if(user.getUsername() == username){
                logger.warning("Username has already been taken.");
                return("Username cannot be empty");
            }
        }

        if(password == null || password.isEmpty()){
            logger.warning("Password cannot be empty.");
            return("Password cannot be empty");
        }

        if(password != confirmPass){
            logger.warning("Passwords do not match.");
            return("Passwords do not match.");
        }
        Chirper newUser = new Chirper(username, password);
        users.add(newUser);

        logger.info("User registered successfully.");
        return("User registered successfully: " + newUser.getUsername());
    }

    public String loginUser(String username, String password){
        for(Chirper user : users){
            if(username == user.getUsername()){
                if(user.checkPassword(password)){
                    logger.info("User logged in successfully.");
                    return("User logged in successfully: " + username);
                }
            }
        }
        logger.warning("Could not find user.");
        return("Could not find user.");
    }
    // methods you'll probably want to add:
    //   registerUser
    //   loginUser
    //   etc.

}
