package com.example.socialcampus.ui.group;

import android.util.Log;

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


    // Må forandres for å passe mot verdiene i databasen (?)
    static final String TABELL_NAVN        = "Post";
    static final String POST_TITLE         = "tittel";
    static final String POST_AUTHOR        = "forfatter";
    static final String POST_GROUP_NAME    = "gruppeNavn";
    static final String POST_DESCRIPTION   = "betegnelse";
    static final String POST_COMMENT_COUNT = "kommentarAntall";
    static final String POST_LIKE_COUNT    = "likesAntall";
    static final String POST_TIME_STAMP    = "tidsPunkt";

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


    public PostCard(JSONObject jsonVare) {
        this.postTitle        = jsonVare.optString(POST_TITLE);
        this.postAuthor       = jsonVare.optString(POST_AUTHOR);
        this.postGroupName    = jsonVare.optString(POST_GROUP_NAME);
        this.postDescription  = jsonVare.optString(POST_DESCRIPTION);
        this.postCommentCount = jsonVare.optString(POST_COMMENT_COUNT);
        this.postLikeCount    = jsonVare.optString(POST_LIKE_COUNT);
        this.postTimestamp    = jsonVare.optString(POST_TIME_STAMP);
    }

    public PostCard() {}

    public static ArrayList<PostCard> lagPostListe(String jsonPostString)
            throws JSONException, NullPointerException {
        ArrayList<PostCard> postListe = new ArrayList<PostCard>();
        JSONObject jsonData  = new JSONObject(jsonPostString);
        JSONArray jsonPostTabell = jsonData.optJSONArray(TABELL_NAVN);
        for(int i = 0; i < jsonPostTabell.length(); i++) {
            JSONObject jsonVare = (JSONObject) jsonPostTabell.get(i);
            PostCard postKort = new PostCard(jsonVare);
            postListe.add(postKort);
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
