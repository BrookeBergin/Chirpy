package edu.georgetown.bll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.georgetown.bll.user.UserServiceTest;
import java.util.List;
import java.util.logging.Logger;
import edu.georgetown.dao.Chirp;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  Class that tests functionality of ChirpService
 */

public class ChirpServiceTest {

    private ChirpService chirpService;
    private Logger testLogger;

    @BeforeEach
    void setUp(){
        testLogger = Logger.getLogger(UserServiceTest.class.getName());
        chirpService = new ChirpService();
    }

    // @Test
    // void testAddChirpValid(){
    //     chirpService.addChirp("hoya", "Hello World");
    //     assertEquals(1, chirpService.getAllChirps().size(), "Chirp should be added successfully");
    // }

    // @Test
    // void testGetAllChirps(){
    //     chirpService.addChirp("hoya", "Hello world");
    //     chirpService.addChirp("saxa", "Goodbye world");

    //     List<Chirp> chirps = chirpService.getAllChirps();
    //     assertEquals(2, chirps.size(), "Should retrieve all chirps.");
    // }

    @Test
    void testGetChirpsForUserValid(){
        chirpService.addChirp("hoya", "First");
        chirpService.addChirp("hoya", "Second");

        List<Chirp> userChirps = chirpService.getChirpsForUser("hoya");
        assertEquals(2, userChirps.size(), "Should retrieve correct number of chirps for user.");
    }

    @Test
    void testGetChirpsForUserInvalid(){
        List <Chirp> userChirps = chirpService.getChirpsForUser("na");
        assertEquals(0, userChirps.size(), "Should retrieve no chirps for a non-existent user.");
    }

    @Test
    void testSearchByHashtagValid(){
        chirpService.addChirp("hoya", "#helloworld !!!!!");
        chirpService.addChirp("saxa", "Random");

        List<Chirp> chirps = chirpService.searchByHashtag("helloworld");
        assertEquals(1, chirps.size(), "Should return chirps with the given hashtag.");
    }

    @Test
    void testSearchByHashtagInvalid(){
        chirpService.addChirp("hoya", "#helloworld !!!!!");
        chirpService.addChirp("saxa", "Random");

        List<Chirp> chirps = chirpService.searchByHashtag("nonexistent");
        assertEquals(0, chirps.size(), "Should return no chirps for a non-existent hashtag.");
    }

    @Test
    void testSearchByHashtagCaseInsensitive(){
        chirpService.addChirp("hoya", "#Helloworld !!!");
        
        List<Chirp> chirps = chirpService.searchByHashtag("helloworld");
        assertEquals(1, chirps.size(), "Should return chirps even with different casing.");
    }

}
