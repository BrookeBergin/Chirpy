


# Chirpy
### The best bird-related social media site

- [My Work](#my-contributions-to-chirpy)
- [Accomplishments](#accomplishments)
- [Description](#description)
    - [What is Chirpy?](#what-is-chirpy)
    - [Code Organization](#code-organization)
- [Usage](#usage)
 

## My Contributions to Chirpy
This social media tool was created as a group project in Spring 2025 Georgetown University Advanced Programming. I served primarily as the group's front-end engineer, creating all of the HTML and CSS for this project. On the backend, I also created the functions for searching/displaying posts by username and hashtag.

Throughout this project, I collaborated with my team of 4 on Github. We made use of individual branches to complete our respective tasks, pushing, pulling, and merging as needed.

Though this was a school assignment, I went a step above the requirements by making the CSS elaborate (as no html/css was required beyond basic functionality). I also worked on the image-posting feature, which was not an assignment requirement.


## Accomplishments
- Worked in a group of 4 to create a signficiant and complex software system
- Wrote a design document
- Wrote source code documentation
- Adhered to a team style guide
- Managed source code in git (including using branches and merges)
- Web programming (interacting with a provided web server)
- Wrote test cases

## Description

### What is Chirpy?

Chirpy is a Twitter-like service in which users (*chirpers*) can create short messages (*chirps*). Chirps can contain #hashtags, which are strings (i.e., without spaces) beginning with a # symbol.

Users must first create an account and then log into the site, at which point they will view the main screen for Chirpy: a *timeline* which contains chirps created by the users's *contacts* (other chirpers which this user follows).

The main screen also features a search option where a user can either;
1. Type the username of another chirpy and view a timeline of that chirper's chirps.
2. Type a #hashtag and view a timeline of chirps (by any user) which contain that hashtag.


### Code Organization

The code in our repo is divided into three main packages:

* [data access objecs (DAO)](/src/main/java/edu/georgetown/dao/): a package containing classes which represent the various data objects used by Chirpy, including representations of a Chirper (user); a Chirp (post); and others.

* [business logic layer (BLL)](/src/main/java/edu/georgetown/bll/): a package containing classes which implement the behaviors and logic of Chirpy.

* [display logic (DL)](/src/main/java/edu/georgetown/dl/): a package containing classes that determine the appearance of our site to the user.


### Usage

You can run Chirpy from inside your Codespaces environment and access the web service via your web browser.

To run Chirpy:
1. Open [Chirpy.java](/src/main/java/edu/georgetown/Chirpy.java)
2. Run the file -- either press F5 when you have `Chirpy.java` open, press the run arrow, or select "Start Debugging" from the left-hand side "hamburger" menu.
3. Click on the PORTS tab at the bottom of your Codespaces environment
4. Click "Forward a Port"
5. Enter '8080' and hit Enter.
6. You'll see something appear under "Forwarded Address".  That's the URL that you'll need to access Chirpy.  It's dependent on your particular Codespaces environment, and will stop working when Codespaces is shutdown.  It's also hard to type.  If you hover over it with your mouse, a globe icon will pop up.  If you click that, it'll open in your browser.

This site is local only. You can log in and out and make as many users & posts as you'd like, but 
* only you will be able to see those posts
* the posts will disappear when you end the run
* you cannot see posts made by other users

Enjoy Chirping!
