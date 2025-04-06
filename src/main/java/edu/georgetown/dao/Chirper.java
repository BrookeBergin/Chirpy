/**
 * A skeleton of a Chirper
 * 
 * Micah Sherr <msherr@cs.georgetown.edu>
 */

package edu.georgetown.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Chirper implements Serializable {
    
    private String username;
    private String password;
    private boolean publicChirps;   
    private List<Chirper> following = new ArrayList<>();


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

    public boolean checkPassword( String password ) {
        return this.password.equals( password );
    }

    public void follow(Chirper user) {
        if (!following.contains(user)) {
            following.add(user);
        }
    }
    
    public List<Chirper> getFollowing() {
        return this.following;
    }

}