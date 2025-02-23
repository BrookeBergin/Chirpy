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


    public Vector<Chirper> getUsers() {
        // not implemented; you'll need to change this
        return users;
    }
    logger.warning("Login failed: username not found");
    return false; //username was not found
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
    }
    // methods you'll probably want to add:
    //   registerUser
    //   loginUser
    //   etc.

}
