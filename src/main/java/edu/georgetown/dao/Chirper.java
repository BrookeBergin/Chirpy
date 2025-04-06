/**
 * A skeleton of a Chirper
 * 
 * Micah Sherr <msherr@cs.georgetown.edu>
 */

package edu.georgetown.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Chirper class is class of Chirpers (users)
 * 
 * @author Anura Sharma, Brooke Bergin, Diane Cho, Kamryn Lee-Caracci
* @version 1.0
* @since 1.0
 */
public class Chirper implements Serializable {
    
    private String username;
    private String password;
    private boolean publicChirps;   
    private List<Chirper> following = new ArrayList<>();


    /**
     * constructor for Chirper class, sets username and password, sets publicChirps to true
     * @param username - username of Chirper
     * @param password - password of Chirper
     */
    public Chirper( String username, String password ) {
        this.username = username;
        this.password = password;
        this.publicChirps = true;
    }

    /**
     * Gets the user's username
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * checks parameter password is equal to Chirper private variable password
     * @param password - password passed into function to check
     * @return boolean true if passwords match, false if they do not match
     */
    public boolean checkPassword( String password ) {
        return this.password.equals( password );
    }

    /**
     * follows user by adding that user to the following list for the current Chirper
     * @param user - user to be added
     */
    public void follow(Chirper user) {
        if (!following.contains(user)) {
            following.add(user);
        }
    }
    
    /**
     * returns list of Chirpers this Chirper is following
     * @return list of Chirpers that are followed by this Chirper
     */
    public List<Chirper> getFollowing() {
        return this.following;
    }

}