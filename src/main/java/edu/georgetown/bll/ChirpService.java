package edu.georgetown.bll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import edu.georgetown.bll.user.UserService;
import edu.georgetown.dao.Chirp;
import edu.georgetown.dao.Chirper;
import java.util.logging.Logger;

public class ChirpService {
    private List<Chirp> chirps = new ArrayList<>();
    private Logger logger;

    public void addChirp(String username, String message) {
        chirps.add(new Chirp(username, message));
    }
    
    public void addChirp(String username, String message, String imagePath) {
        chirps.add(new Chirp(username, message, imagePath));
    }

    public List<Chirp> getAllChirps(List<Chirper> following){
        logger.info("Displaying posts by followers");
        List<Chirp> sortedChirps = new ArrayList<>();

        if (following == null)
        {
            following = new ArrayList<Chirper>();
        }

        for (Chirp chirp : chirps){
            if (following.stream().anyMatch(f -> f.getUsername().equals(chirp.getUsername()))) //chatgpt
            {
                sortedChirps.add(chirp);
                break;
            }
        }
        


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


    // SEARCH FUNCTION 
    // Search chirps by username (alias for getChirpsForUser)
    public List<Chirp> searchByUsername(String username) {
        return getChirpsForUser(username);
    }

    // Search chirps containing a given hashtag (case-insensitive)
    public List<Chirp> searchByHashtag(String hashtag) {
        List<Chirp> result = new ArrayList<>();
        String tag = "#" + hashtag.toLowerCase();

        for (Chirp chirp : chirps) {
            if (chirp.getMessage().toLowerCase().contains(tag)) {
                result.add(chirp);
            }
        }

        result.sort(Comparator.comparing(Chirp::getTimestamp).reversed());
        return result;
    }

    // get chirps from people you follow
    public List<Chirp> getFeedForUser(String username, UserService userService) {
    Chirper user = userService.getUserByUsername(username);
    if (user == null) return new ArrayList<>();

    List<Chirper> following = user.getFollowing();
    List<Chirp> feed = new ArrayList<>();

    for (Chirp chirp : chirps) {
        for (Chirper followee : following) {
            if (chirp.getUsername().equals(followee.getUsername())) {
                feed.add(chirp);
                break;
            }
        }
    }

    feed.sort(Comparator.comparing(Chirp::getTimestamp).reversed());
    return feed;
}


}