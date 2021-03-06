package com.example.socialcampus.ui.group;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostCard {

    //private final int sportImg;
    private String postTitle;
    private String postAuthor;
    private String postGroupNumber;
    private String postDescription;
    private String postCommentCount;
    private String postLikeCount;
    private String postTimestamp;
    private String postId;

    private static final String LOG_TAG = PostCard.class.getSimpleName();


    // Må forandres for å passe mot verdiene i databasen
    static final String POST_TITLE         = "title";
    static final String POST_AUTHOR        = "username";
    static final String POST_GROUP_NAME    = "groupName";
    static final String POST_DESCRIPTION   = "content";
    static final String POST_COMMENT_COUNT = "numOfComments";
    static final String POST_LIKE_COUNT    = "likes";
    static final String POST_TIME_STAMP    = "created";
    static final String POST_ID            = "id";

    public PostCard(String postTitle, String postAuthor, String postGroupNumber, String postDescription,
                    String postCommentCount, String postLikeCount){
        this.postTitle = postTitle;
        this.postAuthor = postAuthor;
        this.postGroupNumber = postGroupNumber;
        this.postDescription = postDescription;
        this.postCommentCount = postCommentCount;
        this.postLikeCount = postLikeCount;
    }

    public PostCard(JSONObject jsonPost) {
        Log.d(LOG_TAG, "PostCard Main Json");
        this.postTitle        = jsonPost.optString(POST_TITLE);
        this.postAuthor       = jsonPost.optString(POST_AUTHOR);
        this.postGroupNumber  = jsonPost.optString(POST_GROUP_NAME);
        this.postDescription  = jsonPost.optString(POST_DESCRIPTION);
        this.postCommentCount = jsonPost.optString(POST_COMMENT_COUNT);
        this.postLikeCount    = jsonPost.optString(POST_LIKE_COUNT);
        this.postTimestamp    = jsonPost.optString(POST_TIME_STAMP);
        this.postId           = jsonPost.optString(POST_ID);

    }

    public PostCard() {}

    public static ArrayList<PostCard> lagPostListe(String jsonPostString)
            throws JSONException, NullPointerException {
        Log.d(LOG_TAG, jsonPostString);
        ArrayList<PostCard> postListe = new ArrayList<PostCard>();
        //JSONObject jsonData  = new JSONObject(jsonPostString);
        // "Vare" Må byttes om til hva nå enn poster heter i databasen
        JSONArray jsonPostTabell = new JSONArray(jsonPostString);
        if(jsonPostTabell != null) {
            for (int i = 0; i < jsonPostTabell.length(); i++) {
                JSONObject jsonPost = (JSONObject) jsonPostTabell.get(i);
                // jsonPost må matche verdiene i databasetabellen post
                PostCard postKort = new PostCard(jsonPost);
                postListe.add(postKort);
            }
        }else {
            Log.d(LOG_TAG, "jsonPostTabell null ");
        }

        return postListe;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonPost = new JSONObject();
        try {
            jsonPost.put(POST_TITLE, this.postTitle);
            jsonPost.put(POST_AUTHOR, 78);
            jsonPost.put(POST_GROUP_NAME, this.postGroupNumber);
            jsonPost.put(POST_DESCRIPTION, this.postDescription);
            jsonPost.put(POST_COMMENT_COUNT, this.postCommentCount);
            jsonPost.put(POST_LIKE_COUNT, this.postLikeCount);
            jsonPost.put(POST_TIME_STAMP, this.postTimestamp);
            jsonPost.put(POST_ID, this.postId);
        }
        catch (JSONException e) {
            return null;
        }
        return jsonPost;
    }


    public static PostCard createPostFromJSONString(String jsonPostString) {
        PostCard nyPost=null;
        try {
            JSONObject jsonPostObject = new JSONObject(jsonPostString);
            if (jsonPostObject!=null)
                Log.d("JSONPostObject", jsonPostObject.toString());
            nyPost = new PostCard(jsonPostObject);
            Log.d("nyPost:", nyPost.toString());
        }
        catch (Exception e) {
        }
        return nyPost;
    }

    public String getPostTitle(){
        return this.postTitle;
    }

    public String getPostAuthor(){
        return this.postAuthor;
    }

    public String getPostGroupName() {
        return this.postGroupNumber;
    }

    public String getPostDescription(){
        return this.postDescription;
    }

    public String getPostCommentCount() {
        return this.postCommentCount;
    }

    public String getPostLikeCount() {
        return this.postLikeCount;
    }

    public String getPostTimestamp() {
        return this.postTimestamp;
    }

    public String getPostId() {
        return this.postId;
    }
}
