package edu.georgetown.bll.user;


import java.util.Vector;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

import edu.georgetown.dao.*;

/**
 * UserService class - containg logic for registration, login, and following 
 * and keeps track of users
 * 
 * @author Anura Sharma, Brooke Bergin, Diane Cho, Kamryn Lee-Caracci
 * @version 1.0
 * @since 1.0
 */
public class UserService {

    private static Logger logger;
    private static Vector<Chirper> registeredUsers = new Vector<>();

    /**
     * Constructor for UserService initializes the logger
     * @param log
     */
    public UserService(Logger log) {
        logger = log;
        logger.info("UserService started");

    }

    /**
     * getUsers() returns a list of users
     * 
     * @return a vector of registered users
     */
    public Vector<Chirper> getUsers() {
        return registeredUsers;
    }

    /**
     * registers a user, checks if username is taken
     * 
     * @param username - The username of the user to be registered
     * @param password - the password to be registered
     * 
     * @return True if successfully registered, false if username taken 
     */
    public boolean registerUser(String username, String password) {
        for (Chirper user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                logger.warning("Registration failed: Username is already taken");
                return false;
            }

        }
        Chirper newUser = new Chirper(username, password);
        registeredUsers.add(newUser);
        logger.info("user registered sucessfully");
        return true;
    }

    /**
     * registers a user
     * checks user age is over 18
     * checks username is not empty
     * checks username is not taken
     * checks password is not empty
     * checks passwords match
     * 
     * @param username - The username of the user to be registered
     * @param password - the password to be registered
     * @param confirm pass - the password confirmation
     * @param age - the age to check
     * 
     * @return True if successfully registered, false if failed
     */
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


    /**
     * logs in a user
     * 
     * @param username - The username of the user
     * @param password - the password
     * 
     * @return True if successfully logged in, false if failed
     */
    public boolean loginUser(String username, String password){
      for (Chirper user: registeredUsers){
        if (user.getUsername().equals(username)){
            if (user.checkPassword(password)){
                logger.info("Login successful for user: " + username);
                return true;
            } else{
                logger.warning("Login failed due to incorrect password for " + username);
                return false;
            }
        }
      }
      logger.warning("Login failed: username not found");
      return false;
    }


    /**
     * follows a user
     * 
     * @param followerUsername - username of follower
     * @param followeeUsername - username of user to be followed
     * 
     * @return true if success, false if failed
     */
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


    /**
     * follows a get list of users that current user is following
     * 
     * @param username - username of current user
     * 
     * @return list of following
     */
    public List<Chirper> getUserFollowing(String username) {
        Chirper user = getUserByUsername(username);
        if (user != null) {
            return user.getFollowing();
        }
        return new ArrayList<>(); // Return empty if user not found
    }
    

    /**
     * get user by username
     * 
     * @param username - username of user to be found
     * 
     * @return Chirper object of retrieved user
     */

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
