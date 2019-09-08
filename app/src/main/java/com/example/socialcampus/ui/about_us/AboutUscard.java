package com.example.socialcampus.ui.about_us;

import com.example.socialcampus.R;

public class AboutUscard {
    private String navn;
    private int bilde;

    public AboutUscard() {
    }

    public String getNavn() {
        return navn;
    }

    public int getBilde() {
        return bilde;
    }

    public static String[] aNavn = new String[] {
            "Sindre",
            "Kristian",
            "Jan Andreas"
    };
    public static int[] aBilder = new int[] {
            R.drawable.sindre,
            R.drawable.kristian,
            R.drawable.jan
    };

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setBilde(int bilde) {
        this.bilde = bilde;
    }
}
