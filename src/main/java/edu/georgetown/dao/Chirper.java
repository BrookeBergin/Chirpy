/**
 * A skeleton of a Chirper
 * 
 * Micah Sherr <msherr@cs.georgetown.edu>
 */

package edu.georgetown.dao;

import java.io.Serializable;
import java.util.Vector;

public class Chirper implements Serializable {
    
    private String username;
    private String password;
    /** if true, the user's chirps are public */
    private boolean publicChirps;   

    /** list of this chirper's followers */
    private Vector<Chirper> followers;
    private Vector<Chirper> following = new Vector<>();


    public Chirper( String username, String password ) {
        this.username = username;
        this.password = password;
        this.publicChirps = true;
        this.followers = new Vector<Chirper>();        
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

    public void addFollower( Chirper follower ) {
        /* followers vector in Chiper inserts follower */
        followers.add(follower);
    }

    public void follow(Chirper user) {
        if (!following.contains(user)) {
            following.add(user);
            user.addFollower(this);
        }
    }
    
    public void unfollow(Chirper user) {
        if (following.contains(user)) {
            following.remove(user);
            user.removeFollower(this);
        }
    }
    
    public void removeFollower(Chirper follower) {
        followers.remove(follower);
    }
    
    public Vector<Chirper> getFollowing() {
        return this.following;
    }

    public Vector<Chirper> getFollowers() {
        return this.followers;
    }

}