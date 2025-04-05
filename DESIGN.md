# Chirpy Design Document

## Overview
Chirpy is a Twitter-like short-message web service where users (chirpers) can create short messages (chirps), follow other chirpers, and search for chirps by username or hashtag. The system consists of three main layers:

- **Data Access Objects (DAO)**: Handles data storage and retrieval.
- **Business Logic Layer (BLL)**: Implements Chirpy's core functionality.
- **Display Logic (DL)**: Manages user interface and page rendering.

## Core Functionalities
### User Management
- **Register a new user**
  - Input: Unique username, password, password confirmation.
  - Validations: Ensure username is unique, password matches confirmation.
  - Storage: Store user data in a dynamically sized data structure (e.g., `HashMap<String, User>`).
- **User Login**
  - Input: Username, password.
  - Validations: Authenticate credentials against stored users.
  - Output: On success, issue an authentication cookie.

### Chirping
- **Submitting a Chirp**
  - Input: Short text message (chirp) with optional hashtags.
  - Additional; can add a picture to their chirp
- **View Timeline**
  - Input: Logged-in user's follow list.
  - Output: Display chirps from followed users in chronological order.

### Search
- **Searching via username**
  - Input: Username.
  - Output: Display chirps from the specified user.
- **Searching via hashtag**
  - Input: Hashtag.
  - Output: Display all chirps containing the specified hashtag.

## Class Design
### Data Access Objects (DAO)
#### User

class User {
    private String username;
    private String password;
    private Vector<String> following;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.following = new Vector<>();
    }
    
    public String getUsername() 
    public String getPassword()
    public Vector<String> getFollowing() 
    public void followUser(String username) 
}

#### Chirp

class Chirp {
    private String username;
    private String message;
    private Date timestamp;
    
    public Chirp(String username, String message) {
        this.username = username;
        this.message = message;
        this.timestamp = new Date();
    }
    
    public String getUsername() { return username; }
    public String getMessage() { return message; }
    public Date getTimestamp() { return timestamp; }
}


### Business Logic Layer (BLL)
#### UserService

class UserService {
    //Retrieve registered users
    method getUsers()
      return registeredUsers
    end methid

    // Register user without age verification
    method registerUser(username, password)
      for each user in registeredUsers
        if user.getUsername() equals username then
          log "registration failed: username is already taken"
          return false
        end if
      end for

      create newUser as Chirper(username, password)
      add newUser to registeredUsers
      log "user registered successfully"
      return true
    end method

    //Register with age verification
    method registerUser(username, password, age)
      if age < 18 then
        log "You must be 18+ to regsiter. "
        return false
      end if

      if username is null or empty then
        log "username cannot be empty."
        return false
      end if

      for each user in registeredUsers
        if user.getUsername() equals username then
          log "username is already taken"
          return false
        end if 
      end for

      if password is null or empty then
        log "password cannot be empty"
        return false
      end if

      create newUser as chirper(username, password)
      add newUser to registeredUsers
      log "user registered successfully."
      return true
    end method

    //login user
    method loginUser(username, password)  
      for each user in registeredUsers
        if user.getUsername() equals username then
          if user.checkpassword(password) then
            log "login successful for user:  " + username
            return true //login success
          else
            log "login failed due to incorrect password for " + username
            return false //incorrect password
          end if
        else
          log "login failed due to incorrect username"
          return false
        end if
      end for
      return false
    end method
end class

}


### Display Logic (DL)
#### Handlers
- **RegisterHandler** (`/register/`): Handles user registration form.
- **LoginHandler** (`/login/`): Processes login requests and sets cookies.
- **TimelineHandler** (`/timeline/`): Displays a logged-in user's timeline.
- **SearchHandler** (`/search/`): Handles search queries by username or hashtag.
- **ListUsersHandler** (`/listusers/`): Displays all registered users (for debugging/testing).

## State Management
- **User Data**: Stored in `HashMap<String, User>`.
- **Chirps**: Stored in `ArrayList<Chirp>`.
- **User Sessions**: Managed via authentication cookies.

## Cookies
Chirpy will use session cookies for authentication. A cookie will store a unique session ID mapping to a logged-in user. This class handles the HTTP requests, extracts cookies from the request and uses a template to format and display cookies in an HTML page. It returns the formatted HTML as an HTTP response. 

## Extra Feature
For Chirpy, our group has chosen to check if the user regisering to become a chirper is above 18 or not. When registering an account, the user will be asked to enter their age and if they happen to be under 18, they will not be able to register an account. Once an 18 or older user is registered and logged in, they will be able to post chirps in form of text and images in jpeg and png form. 


## Functionality of Chirpy:
List and briefly describe the functionalities supported by Chirpy. 
- adding a new user and setting its password
- submitting a chirp
- posting a picture along with your chirp
- searching for chirps via hashtag
- searching for chirps via chirper 
- being able to log out of your account
- Follow other people 


## Classes that make up the DAO
Describe the classes that make up the DAO. Class descriptions should include all of the "public" functions that will be used by the BLL. For example, a User class might include functions such as:
- void setUsername( String username )
- String getPassword()
- Class descriptions should include the method names (i.e., the API) and brief descriptions of what each does and what the parameters and return values are.
- Chirpy class; an overarching class for each chirp

- hashtag should be an object
  

Things that could be taken off for points:
- core functionality
- omissions in the dao
- how list of users will be maintain
- how cookies r used/look it

## CHIRPY 2 includes these :
- A logout functionality that cancels/closes the session of the currently logged in user;
- Posting Chirps;
- Timeline or feed, that displays Chirps;
- Search functionality ; and
- Functional navigation 

