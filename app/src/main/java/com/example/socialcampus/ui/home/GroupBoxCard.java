package com.example.socialcampus.ui.home;

public class GroupBoxCard {

    private final int sportImg;
    private final String sportTitle;
    private final int countMembers;
    private final int countPosts;

    public GroupBoxCard(int sportImg, String sportTitle, int countMembers, int countPosts){
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

    public int getCountMembers(){
        return this.countMembers;
    }

    public int getCountPosts(){
        return this.countPosts;
    }
}
