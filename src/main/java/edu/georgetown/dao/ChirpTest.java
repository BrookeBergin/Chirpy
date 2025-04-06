package edu.georgetown.dao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test Chirp objects
 */
public class ChirpTest {
    private Chirp chirp;

    @BeforeEach
    void setUp(){
        chirp = new Chirp("hoya", "Hello World");
    }

    @Test
    void testGetUsername(){
        assertEquals("hoya", chirp.getUsername(), "Username should match the initial value.");
    }

    @Test
    void testGetMessage(){
        assertEquals("Hello World", chirp.getMessage(), "Message should match the initial value.");
    }

    @Test
    void testGetTimestamp(){
        assertNotNull(chirp.getTimestamp(), "Timestamp should not be null.");
    }

    @Test
    void testChirpCreation(){
        Chirp newChirp = new Chirp("saxa", "Another");
        assertNotNull(newChirp.getTimestamp(), "New chirp should have valid timestamp.");
    }
}
