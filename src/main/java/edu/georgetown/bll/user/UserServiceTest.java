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
        
    }

    //Add some users and test
    @Test 
    void testGetUsesWithUsers(){

    }

    //Test initial get usernames
    @Test 
    void testGetUsernamesEmpty(){

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
