package edu.georgetown.bll.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.logging.Logger;

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


    @Test
    void testRegisterUserValid(){
        userService.getUsers().clear();

        boolean result = userService.registerUser("hoya_saxa", "pass123", "pass123", 20);
        assertTrue(result, "User should be registered successfully.");
        
        assertEquals(1, userService.getUsers().size(), "There should be one registered user.");
        assertEquals("hoya_saxa", userService.getUsers().get(0).getUsername(), "The registered user's username should be 'hoya_saxa'.");
    }

    @Test
    void testRegisterUserDuplicateUsername(){
        userService.registerUser("hoya_saxa", "pass123", "pass123", 20);
        boolean result = userService.registerUser("hoya_saxa", "pass456", "pass456", 19);
        
        assertFalse(result, "Registration should fail when username is already taken.");
    }

    @Test
    void testRegisterUserUnderage(){
        boolean result = userService.registerUser("hoya_saxa", "pass123", "pass123", 12);
        assertFalse(result, "User registration should fail for underage users.");
    }

    @Test
    void testRegisterUserEmptyName(){
        boolean result = userService.registerUser("", "pass123", "pass123", 20);
        assertFalse(result, "User registration should fail with empty username.");
    }

    @Test
    void testLoginUserValid(){
        userService.registerUser("hoya_saxa", "pass123", "pass123", 20);
        boolean result = userService.loginUser("hoya_saxa", "pass123");
        assertTrue(result, "Login should be successful with correct credentials.");
    }

    @Test
    void testLoginUserInvalidPassword(){
        userService.registerUser("hoya_saxa", "pass123", "pass123", 20);
        boolean result = userService.loginUser("hoya_saxa", "wrongpass");
        assertFalse(result, "Login should fail with incorrect password.");
    }

    @Test
    void testLoginUserNotRegistered(){
        boolean result = userService.loginUser("na", "pass123");
        assertFalse(result, "Login should fail for a non-registered user.");
    }
    /* 
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
        userService.registerUser("hoya_saxa", "pass123", "pass123", 20);
        boolean result = userService.registerUser("hoya_saxa", "pass456", "pass456", 19);
        
        assertFalse(result, "Registration should fail when username is already taken.");
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

  */
}
