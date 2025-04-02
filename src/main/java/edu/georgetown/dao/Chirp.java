package edu.georgetown.dao;

import java.io.Serializable;
import java.util.Date;

public class Chirp implements Serializable{
    private String username;
    private String message;
    private Date timestamp;

    public Chirp(String username, String message){
        this.username = username;
        this.message = message;
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

}