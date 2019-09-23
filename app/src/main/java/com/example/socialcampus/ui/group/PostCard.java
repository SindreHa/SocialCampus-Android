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
    private String postGroupName;
    private String postDescription;
    private String postCommentCount;
    private String postLikeCount;
    private String postTimestamp;


    // Må forandres for å passe mot verdiene i databasen
    static final String TABELL_NAVN        = "Post";
    static final String POST_TITLE         = "VNr";
    static final String POST_AUTHOR        = "Betegnelse";
    static final String POST_GROUP_NAME    = "Pris";
    static final String POST_DESCRIPTION   = "KatNr";
    static final String POST_COMMENT_COUNT = "Antall";
    static final String POST_LIKE_COUNT    = "Hylle";
    static final String POST_TIME_STAMP    = "13.37";

    public PostCard(String postTitle, String postAuthor, String postGroupName, String postDescription,
                    String postCommentCount, String postLikeCount, String postTimestamp){
        //this.sportImg = sportImg;
        this.postTitle = postTitle;
        this.postAuthor = postAuthor;
        this.postGroupName = postGroupName;
        this.postDescription = postDescription;
        this.postCommentCount = postCommentCount;
        this.postLikeCount = postLikeCount;
        this.postTimestamp = postTimestamp;
    }

    public PostCard(JSONObject jsonPost) {
        this.postTitle        = jsonPost.optString(POST_TITLE);
        this.postAuthor       = jsonPost.optString(POST_AUTHOR);
        this.postGroupName    = jsonPost.optString(POST_GROUP_NAME);
        this.postDescription  = jsonPost.optString(POST_DESCRIPTION);
        this.postCommentCount = jsonPost.optString(POST_COMMENT_COUNT);
        this.postLikeCount    = jsonPost.optString(POST_LIKE_COUNT);

    }

    public PostCard() {}

    public static ArrayList<PostCard> lagPostListe(String jsonPostString)
            throws JSONException, NullPointerException {
        ArrayList<PostCard> postListe = new ArrayList<PostCard>();
        JSONObject jsonData  = new JSONObject(jsonPostString);
        // "Vare" Må byttes om til hva nå enn poster heter i databasen
        JSONArray jsonPostTabell = jsonData.optJSONArray("Vare");
        if(jsonPostTabell != null) {
            for (int i = 0; i < jsonPostTabell.length(); i++) {
                JSONObject jsonPost = (JSONObject) jsonPostTabell.get(i);
                // jsonPost må matche verdiene i databasetabellen post
                PostCard postKort = new PostCard(jsonPost);
                postListe.add(postKort);
            }
        }else {
            System.out.println("jsonPostTabell null");
        }

        return postListe;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonVare = new JSONObject();
        try {
            jsonVare.put(POST_TITLE, this.postTitle);
            jsonVare.put(POST_AUTHOR, this.postAuthor);
            jsonVare.put(POST_GROUP_NAME, this.postGroupName);
            jsonVare.put(POST_DESCRIPTION, this.postDescription);
            jsonVare.put(POST_COMMENT_COUNT, this.postCommentCount);
            jsonVare.put(POST_LIKE_COUNT, this.postLikeCount);
            jsonVare.put(POST_TIME_STAMP, this.postTimestamp);
        }
        catch (JSONException e) {
            return null;
        }
        return jsonVare;
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
        return this.postGroupName;
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
}
