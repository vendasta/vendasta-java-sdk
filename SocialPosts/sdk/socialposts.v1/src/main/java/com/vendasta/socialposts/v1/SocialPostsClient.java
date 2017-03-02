package com.vendasta.socialposts.v1;

import com.vendasta.socialposts.v1.SocialPostsGrpc.*;
import com.vendasta.socialposts.v1.SocialPostsProtos.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeUnit;
import java.util.Date;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.util.Clock;
import com.google.api.client.util.GenericData;
import com.google.auth.oauth2.ServiceAccountJwtAccessCredentials;
import com.google.protobuf.Timestamp;



/**
 * Client code that makes gRPC calls to the server.
 */
public class SocialPostsClient {
	static final Metadata.Key<String> API_KEY =
		      Metadata.Key.of("x-api-key", Metadata.ASCII_STRING_MARSHALLER);
	static final Metadata.Key<String> AUTHORIZATION =
		      Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
	static final String audience = "https://www.googleapis.com/oauth2/v4/token";
	static final int port = 443;

    private final String host;
    private final String scope;
    private final String apiKey;
    private final String apiUser;
    private final ManagedChannel channel;
    private final SocialPostsBlockingStub blockingStub;
    
    private String idToken;
    
    /** Construct client for accessing RouteGuide server at {@code host:port}. 
     * @param host - the host of the api (with out the https:// prefix)
     * @param apiUser - your api
     * @param apiKey - the api key provided
     * @param serviceAccountPath - a path to the service account json file
     * @throws URISyntaxException if the host is int a valid uri
     * @throws IOException if the path to the service account key is not valid
     * */
    public SocialPostsClient(String host, String apiUser, String apiKey, String serviceAccountPath) throws IOException, URISyntaxException {
        this.host = host;
        this.scope = "https://" + this.host;
        this.apiKey = apiKey;
        this.apiUser = apiUser;
        this.idToken = this.getIDTokenFromServiceAccount(serviceAccountPath);
        
        Metadata header = new Metadata();
        header.put(SocialPostsClient.API_KEY, this.apiKey);
        header.put(SocialPostsClient.AUTHORIZATION, "Bearer " + this.idToken);
        this.channel = ManagedChannelBuilder.forAddress(this.host, SocialPostsClient.port).build();
        SocialPostsBlockingStub stub = SocialPostsGrpc.newBlockingStub(this.channel);
        this.blockingStub = MetadataUtils.attachHeaders(stub, header); 
    }
    
    
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    
    /** RPC for getting the first page of social posts 
     * @param accountID - will be in the format of AG-XXXXXXXX
     * @param startTime - the time before the earliest post to lookup (optional)
     * @param endTime - the time after the latest post to lookup (optional, defaults to now)
     * @return {@link ListSocialPostResponse}
    */
    public ListSocialPostResponse list(String accountID, long startTime, long endTime ) {
    	Timestamp start = Timestamp.newBuilder().setSeconds(startTime / 1000)
    	.setNanos((int) ((startTime % 1000) * 1000000)).build();
    	Timestamp end = Timestamp.newBuilder().setSeconds(endTime / 1000)
    	    	.setNanos((int) ((endTime % 1000) * 1000000)).build();
    	ListSocialPostRequest request = ListSocialPostRequest.newBuilder()
    			.setPartnerId(apiUser)
    			.setAccountId(accountID)
    			.setStart(start)
    			.setEnd(end)
    			.build();
    	
    	ListSocialPostResponse response;
    	response = blockingStub.list(request);
    	    	
    	return response;
    }
    
    /** RPC for getting next pages of social posts
     * @param accountID - will be in the format of AG-XXXXXXXX
     * @param cursor - would have been returned by {@link SocialPostsClient#list(String, long, long)} used to rebuild the previous query and fetch the next page
     * @return {@link ListSocialPostRequest}
    */
    public ListSocialPostResponse list(String accountID, String cursor) {
    	ListSocialPostRequest request = ListSocialPostRequest.newBuilder()
                .setPartnerId(apiUser)
    			.setAccountId(accountID)
    			.setNextCursor(cursor)
    			.build();
    	
    	ListSocialPostResponse response;
   		response = blockingStub.list(request); 	
    	return response;
    }
    
    
    
    /** Helper function for getting ID token from service account
     * @throws IOException 
     * @throws URISyntaxException */
    private String getIDTokenFromServiceAccount(String serviceAccountPath) throws IOException, URISyntaxException {
    	
    	URI audienceUri = new URI(SocialPostsClient.audience);
    	
    	String jwtAccess = this.getJWTAccessFromServiceAccount(serviceAccountPath);
    	
        GenericData tokenRequest = new GenericData();
        tokenRequest.set("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
        tokenRequest.set("assertion", jwtAccess);
        UrlEncodedContent content = new UrlEncodedContent(tokenRequest);

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(audienceUri), content);
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        request.setParser(new JsonObjectParser(jsonFactory));

        HttpResponse response;
        try {
          response = request.execute();
        } catch (IOException e) {
          throw new IOException("Error getting access token for service account: ", e);
        }

        GenericData responseData = response.parseAs(GenericData.class);
        
        return (String) responseData.get("id_token");   
    }
    
    /** Helper function for getting JWT from ServiceAccountJwtAccessCredentials
     * @throws IOException 
     * @throws URISyntaxException */
    private String getJWTAccessFromServiceAccount(String serviceAccountPath) throws IOException, URISyntaxException {
    	InputStream is = new FileInputStream(serviceAccountPath);
    	ServiceAccountJwtAccessCredentials creds = ServiceAccountJwtAccessCredentials.fromStream(is);
    	
    	URI audienceUri = new URI(SocialPostsClient.audience);
    	
    	JsonWebSignature.Header header = new JsonWebSignature.Header();
        header.setAlgorithm("RS256");
        header.setType("JWT");
        header.setKeyId(creds.getPrivateKeyId());

        JsonWebToken.Payload payload = new JsonWebToken.Payload();
        Clock clock = Clock.SYSTEM;
        long currentTime = clock.currentTimeMillis();
        // Both copies of the email are required
        payload.setIssuer(creds.getClientEmail());
        payload.setSubject(creds.getClientEmail());
        payload.setAudience(audienceUri.toString());
        payload.setIssuedAtTimeSeconds(currentTime / 1000);
        payload.setExpirationTimeSeconds(currentTime / 1000 + 3600);
        payload.put("scope", this.scope);
        
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        String jwtAccess;
        try {
        	jwtAccess = JsonWebSignature.signUsingRsaSha256(
              creds.getPrivateKey(), jsonFactory, header, payload);
        } catch (GeneralSecurityException e) {
          throw new IOException("Error signing service account JWT access header with private key:", e);
        }
        
        return jwtAccess;
    }
    
}