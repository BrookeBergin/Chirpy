package edu.georgetown.bll.user;


import java.util.Vector;
import java.util.logging.Logger;

import edu.georgetown.dao.*;

public class UserService {

 private static Logger logger;
 private static Vector<Chirper> registeredUsers = new Vector<>();

    public UserService(Logger log) {
        logger = log;
        logger.info("UserService started");
    } 
   //Register a user
   public Vector<Chirper> getUsers() {   
    return registeredUsers;
}
public boolean registerUser(String username, String password){
    //checking if username is already taken
    for (Chirper user: registeredUsers){
        if (user.getUsername().equals(username)){
            logger.warning("Registration failed: Username is already taken");
            return false;
        }
    }
    Chirper newUser = new Chirper(username, password);
    registeredUsers.add(newUser);
    logger.info("user registered sucessfully");
    return true;
}


 //Log in a user
 public boolean loginUser(String username, String password){
    for (Chirper user: registeredUsers){
        if (user.getUsername().equals(username)){
            if (user.checkPassword(password)){
                logger.info("Login successful for user: " + username);
                return true;//the login was successful
            } else{
                logger.warning("Login failed due to incorrect password for " + username);
                return false; //incorrect password entered
            }
        }
    }
    logger.warning("Login failed: username not found");
    return false; //username was not found
 }

    // methods you'll probably want to add:
    //   registerUser
    //   loginUser
    //   etc.

}
