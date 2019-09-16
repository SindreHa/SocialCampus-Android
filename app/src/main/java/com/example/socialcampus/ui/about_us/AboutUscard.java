package com.example.socialcampus.ui.about_us;

import com.example.socialcampus.R;

public class AboutUscard {

    private final int aboutUsImg;
    private final String aboutUsTitle;
    private final String aboutUsRole;
    private final String aboutUsDescription;

    private String[] navn = new String[] {
            "Sindre",
            "Kristian",
            "Jan Andreas"
    };

    private int[] bilde = new int[] {
                R.drawable.sindre,
                R.drawable.kristian,
                R.drawable.jan
    };

    public AboutUscard(int aboutUsImg, String aboutUsTitle, String aboutUsRole, String aboutUsDescription) {
        this.aboutUsImg = aboutUsImg;
        this.aboutUsTitle = aboutUsTitle;
        this.aboutUsRole = aboutUsRole;
        this.aboutUsDescription = aboutUsDescription;
    }

    public String getAboutUsTitle() {
        return aboutUsTitle;
    }

    public int getBilde() {
        return aboutUsImg;
    }

    public String getAboutUsRole() {
        return aboutUsRole;
    }

    public String getAboutUsDescription() {
        return aboutUsDescription;
    }
}
