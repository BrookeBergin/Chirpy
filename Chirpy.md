# Chirpy Design Document
**Functionality of Chirpy:**
List and briefly describe the functionalities supported by Chirpy. This should include, for example:
adding a new user and setting its password
submitting a chirp
rendering a timeline
searching for chirps via hashtag
searching for chirps via chirper (i.e., searching for MicahSherr1's chirps)
Describe the classes that make up the DAO. Class descriptions should include all of the "public" functions that will be used by the BLL. For example, a User class might include functions such as:
void setUsername( String username )
String getPassword()
Class descriptions should include the method names (i.e., the API) and brief descriptions of what each does and what the parameters and return values are.
Your design document should describe how the various class objects will be stored. Initially, you may store everything in memory (yikes!) -- e.g., registered users could persist in a Vector or HashMap. Be sure to also describe how a chirper's contacts are stored/maintained.
Describe what your cookies look like (see below).
Similarly, describe the classes that make up the BLL. Here, classes will refer to your service's main functionalities. For example, you might have a classes called SearchFunctionality and ChirpHandler.

**Extra: Support for attaching an image to the Chirp**
