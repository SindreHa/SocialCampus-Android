package com.example.socialcampus.ui.home;

public class GroupBoxCard {

    private final int drawable;
    private final String description;

    public GroupBoxCard(int drawable, String description){
        this.drawable = drawable;
        this.description = description;
    }

    public int getImagePath(){
        return this.drawable;
    }

    public String getDescription(){
        return this.description;
    }
}
