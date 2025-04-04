package edu.georgetown.dao;

import java.io.Serializable;
import java.util.Date;

public class Chirp implements Serializable{
    private String username;
    private String message;
    private Date timestamp;
    private String imagePath; 


    public Chirp(String username, String message) {
        this.username = username;
        this.message = message;
        this.timestamp = new Date();
    }

    public Chirp(String username, String message, String imagePath) {
        this.username = username;
        this.message = message;
        this.imagePath = imagePath;
        this.timestamp = new Date();
    }

    public String getUsername(){
        return username;
    }

    public String getMessage(){
        return message;
    }

    public Date getTimestamp(){
        return timestamp;
    }

    public String getImageFilename(){
         return imagePath; }

}