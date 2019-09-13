package com.example.socialcampus.ui.group;

public class PostCard {

    //private final int sportImg;
    private final String postTitle;
    private final String postAuthor;
    private final String postDescription;

    public PostCard(String postTitle, String postAuthor, String postDescription){
        //this.sportImg = sportImg;
        this.postTitle = postTitle;
        this.postAuthor = postAuthor;
        this.postDescription = postDescription;
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

    public String getPostDescription(){
        return this.postDescription;
    }
}
