# Quick Start With Maven
Add the following to your pom file
```
<dependencies>
    <dependency>
        <groupId>com.vendasta</groupId>
        <artifactId>socialposts.v1</artifactId>
        <version>1.0.1</version>
    </dependency>
</dependencies>
```


# Overview of Interfaces
## Client
Used to communicate with the server.
Constructed with a valid apiKey, apiUser, host and a private key.

### Methods
#### SocialPosts List(String accountID, long startTime, long endTime)
Returns a list of the SocialPosts, if there are more that need to be paged through, and the cursor to use to grab the next page

#### SocialPosts List(String accountID, String cursor)
Returns a list of the SocialPosts, if there are more that need to be paged through, and the cursor to use to grab the next page


# Example Code
See `sdk-usage/SocialPosts` for an example instantiation of the client and listing all of the social posts for an account
