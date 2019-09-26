package com.example.socialcampus.ui.LogIn;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logger på brukeren ved å lagre brukerinformasjon og sette en session
     *
     * @param username
     * @param fullName
     */
    public void loginUser(String username, String fullName) {
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_FULL_NAME, fullName);
        Date date = new Date();

        //Setter en session for de neste 7 dagene
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    /**
     * Sjekker om bruker er logget inn
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* Om shared preferences ikke har en verdi er ikke brukeren logget inn
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Sjekker om session er utgått ved å sammenligne dagens date med expiryDate
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Henter og returner brukerdetaljer
     *
     * @return user details
     */
    public User getUserDetails() {
        //Sjekker først om bruker er logget inn
        if (!isLoggedIn()) {
            return null;
        }
        User user = new User();
        user.setUsername(mPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        user.setFullName(mPreferences.getString(KEY_FULL_NAME, KEY_EMPTY));
        user.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));

        return user;
    }

    /**
     * Clear session = logger ut
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }

}