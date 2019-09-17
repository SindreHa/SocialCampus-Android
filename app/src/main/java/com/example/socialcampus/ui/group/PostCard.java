package com.example.socialcampus.ui.group;

public class PostCard {

    //private final int sportImg;
    private final String postTitle;
    private final String postAuthor;
    private final String postGroupName;
    private final String postDescription;
    private final String postCommentCount;
    private final String postLikeCount;
    private final String postTimestamp;

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

    /*public int getImagePath(){
        return this.sportImg;
    }*/

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
