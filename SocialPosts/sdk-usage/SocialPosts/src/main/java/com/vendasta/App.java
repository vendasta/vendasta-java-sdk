package com.vendasta;

import java.io.IOException;
import java.net.URISyntaxException;

import com.vendasta.socialposts.v1.SocialPostsClient;
import com.vendasta.socialposts.v1.SocialPostsProtos.ListSocialPostResponse;

/**
 * App!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException, IOException, URISyntaxException
    {
    	String serviceAccoutPath = "/path/to/key.json";
		String apiKey = "APIKEY";
		String apiUser = "APIUSER";
		String accountID = "AG-XXXXXXXX";
		long startTime = System.currentTimeMillis() - 31536000000L;
		long endTime = System.currentTimeMillis();
		
		
		SocialPostsClient client = new SocialPostsClient("host", apiUser, apiKey, serviceAccoutPath);
		
		try {
			
			ListSocialPostResponse response = client.list(accountID, startTime, endTime);
			//System.out.println("Successfully call social post server: " + response.getSocialPostsList());
			System.out.println("Social post count: " + response.getSocialPostsCount());
			System.out.println("Next cursor: " + response.getNextCursor());
			while (response.getHasMore()) {
				String cursor = response.getNextCursor();
				response = client.list(accountID, cursor);
				System.out.println("Social post count: " + response.getSocialPostsCount());
				//System.out.println("Next cursor: " + response.getNextCursor());
			}
			
		} finally {
			client.shutdown();
		}
    }
}
