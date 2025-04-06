
package edu.georgetown.dao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChirperTest {
    private Chirper chirper;

    @BeforeEach
    void setUp(){
        chirper = new Chirper("hoya", "pass123");
    }

    @Test
    void testGetUsername(){
        assertEquals("hoya", chirper.getUsername(), "Username should math the initialized value.");
    }

    @Test
    void testCheckPasswordCorrect(){
        assertTrue(chirper.checkPassword("pass123"),"Password check should succeed with correct password.");
    }

    @Test
    void testCheckPasswordIncorrect(){
        assertFalse(chirper.checkPassword("wrong"), "Password check should fail with incorrect password.");
    }

    // @Test
    // void testAddFollower(){
    //     Chirper follower = new Chirper("follower1", "pass123");
    //     chirper.addFollower(follower);
    //     assertEquals(1, chirper.getFollowers().size(), "Follower should be added successfully.");
    // }

    // @Test
    // void testAddMultipleFollowers(){
    //     Chirper follower1 = new Chirper("follower1", "pass123");
    //     Chirper follower2 = new Chirper("follower2", "pass456");
    //     chirper.addFollower(follower1);
    //     chirper.addFollower(follower2);

    //     assertEquals(2, chirper.getFollowers().size(), "Multiple followers should be added successfully.");
    // }
}
