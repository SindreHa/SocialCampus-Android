package com.example.socialcampus.ui.home;

public class GroupBoxCard {

    private final int sportImg;
    private final String sportTitle;
    private final String countMembers;
    private final String countPosts;

    public GroupBoxCard(int sportImg, String sportTitle, String countMembers, String countPosts){
        this.sportImg = sportImg;
        this.sportTitle = sportTitle;
        this.countMembers = countMembers;
        this.countPosts = countPosts;
    }

    public int getImagePath(){
        return this.sportImg;
    }

    public String getDescription(){
        return this.sportTitle;
    }

    public String getCountMembers(){
        return this.countMembers;
    }

    public String getCountPosts(){
        return this.countPosts;
    }
}
