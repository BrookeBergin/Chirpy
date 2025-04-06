package edu.georgetown.bll.user;


import java.util.Vector;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

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

    public boolean registerUser(String username, String password, String confirmPass, int age){
        if(age < 18){
            logger.warning("You must be 18+ to register as a Chirper. Come back in " + (18 - age) + " years!");
            return false;
        }
        
        if(username == null || username.isEmpty()){
            logger.warning("Username cannot be empty.");
            return false;
        }
        
        for(Chirper user : registeredUsers){
            if(user.getUsername().equals(username)){
                logger.warning("Username has already been taken.");
                return false;
            }
        }

        if(password == null || password.isEmpty()){
            logger.warning("Password cannot be empty.");
            return false;
        }

        if(!(password.equals(confirmPass))){
            logger.warning("Passwords do not match.");
            return false;
        }

        Chirper newUser = new Chirper(username, password);
        registeredUsers.add(newUser);
        logger.info("User registered successfully.");
        return true;
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
      logger.warning("Login failed: username not found");
      return false;
    }
    
    public boolean followUser(String followerUsername, String followeeUsername) {
        Chirper follower = getUserByUsername(followerUsername);
        Chirper followee = getUserByUsername(followeeUsername);
    
        if (follower != null && followee != null && !follower.getFollowing().contains(followee)) {
            follower.follow(followee);
            logger.info(followerUsername + " followed " + followeeUsername);
            return true;
        }
        return false;
    }

    public List<Chirper> getUserFollowing(String username) {
        Chirper user = getUserByUsername(username);
        if (user != null) {
            return user.getFollowing();
        }
        return new ArrayList<>(); // Return empty if user not found
    }
    
    public Chirper getUserByUsername(String username) {
        for (Chirper user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                logger.info("getting user by username: " + user);
                return user;
            }
        }
        return null;
    }

}
