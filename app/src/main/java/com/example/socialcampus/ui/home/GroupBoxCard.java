package com.example.socialcampus.ui.home;

public class GroupBoxCard {

    private final int groupImg;
    private final String groupTitle;
    private final String countMembers;
    private final String countPosts;

    public GroupBoxCard(int groupImg, String groupTitle, String countMembers, String countPosts){
        this.groupImg = groupImg;
        this.groupTitle = groupTitle;
        this.countMembers = countMembers;
        this.countPosts = countPosts;
    }

    public int getImagePath(){
        return this.groupImg;
    }

    public String getDescription(){
        return this.groupTitle;
    }

    public String getCountMembers(){
        return this.countMembers;
    }

    public String getCountPosts(){
        return this.countPosts;
    }
}
