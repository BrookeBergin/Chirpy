package edu.georgetown.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * Chirp class for posts containing a username, message, timestamp, and imagePath
 * 
 * @author Anura Sharma, Brooke Bergin, Diane Cho, Kamryn Lee-Caracci
 * @version 1.0
 * @since 1.0
 */
public class Chirp implements Serializable{
    private String username;
    private String message;
    private Date timestamp;
    private String imagePath; 

    /**
     * constructor for Chirp class using username and password
     * @param username
     * @param message
     */
    public Chirp(String username, String message) {
        this.username = username;
        this.message = message;
        this.timestamp = new Date();
    }

    /**
     * constructor for chirp class using username and password and imagePath
     * @param username
     * @param message
     * @param imagePath
     */
    public Chirp(String username, String message, String imagePath) {
        this.username = username;
        this.message = message;
        this.imagePath = imagePath;
        this.timestamp = new Date();
    }

    /** 
     * returns the username of the Chirp
     * @return username of post
     */
    public String getUsername(){
        return username;
    }

    /** 
     * returns the message of the Chirp
     * @return message of post
     */
    public String getMessage(){
        return message;
    }

    /** 
     * returns the timestamp of the Chirp
     * @return timestamp of post
     */
    public Date getTimestamp(){
        return timestamp;
    }

    /** 
     * returns the image path of the Chirp
     * @return image path of post
     */
    public String getImageFilename(){
         return imagePath; }

}