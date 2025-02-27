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
  - Storage: Store chirp data in memory (`Vector<Chirp>` or similar).
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
```java
class User {
    private String username;
    private String password;
    private Vector<String> following;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.following = new Vector<>();
    }
    
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Vector<String> getFollowing() { return following; }
    public void followUser(String username) { following.add(username); }
}
```
#### Chirp
```java
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
```

### Business Logic Layer (BLL)
#### UserService
```java
class UserService {
    private Map<String, User> users = new HashMap<>();
    
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) return false;
        users.put(username, new User(username, password));
        return true;
    }
    
    public boolean authenticateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).getPassword().equals(password);
    }
}
```
#### ChirpService
```java
class ChirpService {
    private List<Chirp> chirps = new ArrayList<>();
    
    public void postChirp(String username, String message) {
        chirps.add(new Chirp(username, message));
    }
    
    public List<Chirp> searchByUser(String username) {
        return chirps.stream().filter(c -> c.getUsername().equals(username)).collect(Collectors.toList());
    }
    
    public List<Chirp> searchByHashtag(String hashtag) {
        return chirps.stream().filter(c -> c.getMessage().contains("#" + hashtag)).collect(Collectors.toList());
    }
}
```

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
Chirpy will use session cookies for authentication. A cookie will store a unique session ID mapping to a logged-in user:
```java
class SessionManager {
    private Map<String, String> sessionMap = new HashMap<>(); // sessionID -> username
    
    public String createSession(String username) {
        String sessionId = UUID.randomUUID().toString();
        sessionMap.put(sessionId, username);
        return sessionId;
    }
    
    public String getUserFromSession(String sessionId) {
        return sessionMap.get(sessionId);
    }
}
```

## Extra Feature
For Chirpy, our group has chosen to check if the user regisering to become a chirper is above 18 or not. When registering an account, the user will be asked to enter their age and if they happen to be under 18, they will be given a message on how they will only be able to post pictures as their chirp when they are 18 and above. 



## Functionality of Chirpy:
List and briefly describe the functionalities supported by Chirpy. 
  This should include, for example:
- adding a new user and setting its password
- submitting a chirp
- rendering a timeline
- searching for chirps via hashtag
- searching for chirps via chirper (i.e., searching for MicahSherr1's chirps)


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



