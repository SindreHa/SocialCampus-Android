package com.example.socialcampus.ui.about_us;

import com.example.socialcampus.R;

public class AboutUscard {

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

    public AboutUscard() {
    }

    public String[] getNavn() {
        return navn;
    }

    public int[] getBilde() {
        return bilde;
    }

}
