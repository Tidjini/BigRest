package com.soft.big.bigrest.UI.Fragments;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.soft.big.bigrest.R;

/**
 * Created by Tidjini on 26/11/2017.
 * Add simple activity Config
 * Add config fragment that extends from PreferenceFragmentCompat
 * Add xml folder in res where preferences xml files will live
 *      inside xml folder create config preference xml file
 * Add the reference in th fragment
 * Add the fragment to the activity
 * Add preference theme required or the app crash
 */

public class ConfigurationFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.bigrest_preferences);
    }
}
