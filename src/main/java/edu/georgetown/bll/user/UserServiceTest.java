package edu.georgetown.bll.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.logging.Logger;
import edu.georgetown.dao.Chirper;
import freemarker.ext.beans.TemplateAccessible;

public class UserServiceTest {
    private UserService userService;
    private Logger testLogger;

    // initializes the objects for testing
    @BeforeEach
    void setUp(){
        //initializes the logger and UserService
        testLogger = Logger.getLogger(UserServiceTest.class.getName());
        userService = new UserService(testLogger);
        
    }

    //Testing initial get user
    @Test 
    void testGetUsersEmpty(){
        int userCount = userService.getUsers().size();
        assertEquals(0, userCount, "There should be no users initially.");
        assertNotNull(userService.getUsers(), "Users vector should not be null");    }

    //Add some users and test
    @Test 
    void testGetUsesWithUsers(){
        userService.registerUser("hoya_saxa", "password123", "password123", 20);
        userService.registerUser("saxa_hoya", "password456", "password456", 25);
        
        int userCount = userService.getUsers().size();

        assertNotNull(userService.getUsers(), "Users vector should not be null");
        assertEquals(2, userCount, "There should be 2 users after registration.");

        boolean hoyaFound = false;
        boolean saxaFound = false;
        for(Chirper user : userService.getUsers()){
            if(user.getUsername().equals("hoya_saxa")){
                hoyaFound = true;
            }
            if(user.getUsername().equals("saxa_hoya")){
                saxaFound = true;
            }
            
        }
        assertTrue(hoyaFound, "User 'hoya_saxa' is in the users vector");
        assertTrue(saxaFound, "User 'saxa_hoya' is in the users vector");

    }

    //Test initial get usernames
    @Test 
    void testGetUsernamesEmpty(){
        boolean usernameExists = userService.getUsers().stream().anyMatch(user->user.getUsername().equals("hoya_saxa"));

    }

    //Tests that user can be registered and retrived
    @Test
    void testGetUsernamesWithUsers(){
        userService.registerUser("george_town", "password123");

        boolean userExists = userService.getUsers().stream().anyMatch(user -> user.getUsername().equals("george_town"));

        //assert

    }

    @Test 
    void testRegisterUserSuccess(){

    }

    @Test 
    void testRegisterUserDuplicate(){

    }

    @Test
    void testRegisterUserWithConfirmPasswordMismatch(){

    }

    @Test 
    void testLoginUserSuccess(){

    }

    @Test 
    void testLoginUserIncorrectPassord(){

    }

    @Test 
    void testLoginUserNotFound(){

    }

    @Test 
    void testGetUser(){

    }


}
