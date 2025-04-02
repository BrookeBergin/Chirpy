package edu.georgetown.bll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import edu.georgetown.dao.Chirp;

public class ChirpService {
    private List<Chirp> chirps = new ArrayList<>();

    public void addChirp(String username, String message){
        chirps.add(new Chirp(username, message));
    }

    public List<Chirp> getAllChirps(){
        //Return chirps sorted by timestamp descending
        List<Chirp> sortedChirps = new ArrayList<>(chirps);
        Collections.sort(sortedChirps, new Comparator<Chirp>(){
            public int compare(Chirp c1, Chirp c2){
                return c2.getTimestamp().compareTo(c1.getTimestamp());
            }
        });
        return sortedChirps;
    }   

    public List<Chirp> getChirpsForUser(String username){
        List<Chirp> userChirps = new ArrayList<>();
        for(Chirp chirp : chirps){
            if(chirp.getUsername().equals(username)){
                userChirps.add(chirp);
            }
        }

        //Sort descending by timestamp
        Collections.sort(userChirps, new Comparator<Chirp>(){
            public int compare(Chirp c1, Chirp c2){
                return c2.getTimestamp().compareTo(c1.getTimestamp());
            }
            });
            return userChirps;
    }

}