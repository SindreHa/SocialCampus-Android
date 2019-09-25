package com.example.socialcampus.ui.settings;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socialcampus.MainActivity;
import com.example.socialcampus.R;
import com.google.android.material.snackbar.Snackbar;

public class fragment_settings extends PreferenceFragmentCompat {

    String LOG_CAT = fragment_settings.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        //https://stackoverflow.com/questions/4005029/how-can-i-set-the-android-preference-summary-text-color/4047054#4047054
        Preference app_version = findPreference("app_version");
        Spannable summary = new SpannableString(getString(R.string.versionName));
        summary.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textWhiteSecondary)), 0, summary.length(), 0);
        app_version.setSummary(summary);

        /*
        * Metode for å åpne mailklient for å sende tilbakemelding
        * https://stackoverflow.com/questions/5330677/android-preferences-onclick-event
        */

        Preference tilbakemeldingMail = findPreference("tilbakemelding");
        tilbakemeldingMail.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"soscialcampus@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Tilbakemelding for app");
                i.putExtra(Intent.EXTRA_TEXT   , "AppVersjon: 1.2");
                try {
                    //Viser vindu bruker kan velge mailklient i
                    startActivity(Intent.createChooser(i, "Send tilbakemelding"));
                } catch (android.content.ActivityNotFoundException ex) {
                    //Viser snackbar som sier at ingen mailklient ble funnet
                    final Snackbar snackBar = Snackbar.make(getView(), "Ingen mailklient funnet", Snackbar.LENGTH_LONG);
                    snackBar.setAction("Skjul", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                }
                return true;
            }
        });

    }

}
