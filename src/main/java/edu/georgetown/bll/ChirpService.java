package edu.georgetown.bll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.georgetown.bll.user.UserService;
import edu.georgetown.dao.Chirp;
import edu.georgetown.dao.Chirper;

/**
 * ChirpService class
 * 
 * logic for creating and managing chips (posts on Chirpy application).
 * 
 * @author Anura Sharma, Brooke Bergin, Diane Cho, Kamryn Lee-Caracci
 * @version 1.0
 * @since 1.0
 */
public class ChirpService {
    private List<Chirp> chirps = new ArrayList<>();

    /** Posts a chirp (message, no image)
     * 
     * @param username - username of poster
     * @param message - the message in the post
     */
    public void addChirp(String username, String message) {
        chirps.add(new Chirp(username, message));
    }

     /** Posts a chirp (message and image)
     * 
     * @param username - username of poster
     * @param message - the message in the post
     * @string imagePath - the path to the image to be posted
     */
    
    public void addChirp(String username, String message, String imagePath) {
        chirps.add(new Chirp(username, message, imagePath));
    }

    // ---- unused fallback functions -------
    // public List<Chirp> getFollowingChirps(List<Chirper> following){
    //     logger.info("Displaying posts by followers");
    //     List<Chirp> sortedChirps = new ArrayList<>();

    //     for (Chirp chirp : chirps){
    //         if (following.stream().anyMatch(f -> f.getUsername().equals(chirp.getUsername()))) //chatgpt
    //         {
    //             sortedChirps.add(chirp);
    //         }
    //     }
        
    //     Collections.sort(sortedChirps, new Comparator<Chirp>(){
    //         public int compare(Chirp c1, Chirp c2){
    //             return c2.getTimestamp().compareTo(c1.getTimestamp());
    //         }
    //     });
    
    //     return sortedChirps;
    // }
    
    // public List<Chirp> getAllChirps(){
    //     logger.info("Displaying all posts");
    //     List<Chirp> sortedChirps = new ArrayList<>();
    //     Collections.sort(sortedChirps, new Comparator<Chirp>(){
    //         public int compare(Chirp c1, Chirp c2){
    //             return c2.getTimestamp().compareTo(c1.getTimestamp());
    //         }
    //     });
        
    //     return sortedChirps;
    // }   
    // ---- unused fallback functions end -------

    /**
     * gets chiprs posted by a specific user sorted by timestamp
     * @param username - the user to get the posts of
     * @return a list of chirps by the specified user
     */
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

    /**
     * search chirps by username (alias for getChrpsForUser)
     * @param username
     * @return a list of chirps by that user
     */
    public List<Chirp> searchByUsername(String username) {
        return getChirpsForUser(username);
    }

    /**
     * Search chirps containing a given hashtag (case-insensitive)
     * @param hashtag - # to be searched for
     * @return list of chirps containing that hashtag
     */
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

    // /**
    //  * get chirps from people you follow
    //  * @param username - username of logged in user
    //  * @param userService - user service currently active
    //  * @return list of chirps by people you follow
    //  */
    // public List<Chirp> getFeedForUser(String username, UserService userService) {
    //     Chirper user = userService.getUserByUsername(username);
    //     if (user == null)
    //         return new ArrayList<>();

    //     List<Chirper> following = user.getFollowing();
    //     List<Chirp> feed = new ArrayList<>();

    //     for (Chirp chirp : chirps) {
    //         for (Chirper followee : following) {
    //             if (chirp.getUsername().equals(followee.getUsername())) {
    //                 feed.add(chirp);
    //                 break;
    //             }
    //         }
    //     }

    //     feed.sort(Comparator.comparing(Chirp::getTimestamp).reversed());
    //     return feed;
    // }

}