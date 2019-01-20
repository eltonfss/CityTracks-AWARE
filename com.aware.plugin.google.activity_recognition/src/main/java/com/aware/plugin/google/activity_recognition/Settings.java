package com.aware.plugin.google.activity_recognition;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.aware.Aware;

public class Settings extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    /**
     * State of Google's Activity Recognition plugin
     */
    public static final String STATUS_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION = "status_plugin_google_activity_recognition";

    /**
     * Frequency of Google's Activity Recognition plugin in seconds<br/>
     * By default = 60 seconds
     */
    public static final String FREQUENCY_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION = "frequency_plugin_google_activity_recognition";

    private static CheckBoxPreference status;
    private static EditTextPreference frequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        status = (CheckBoxPreference) findPreference(STATUS_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION);
        if (Aware.getSetting(getApplicationContext(), STATUS_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION).length() == 0) {
            Aware.setSetting(this, STATUS_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION, true);
        }
        status.setChecked(Aware.getSetting(getApplicationContext(), STATUS_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION).equals("true"));

        frequency = (EditTextPreference) findPreference(FREQUENCY_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION);
        if (Aware.getSetting(this, FREQUENCY_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION).length() == 0) {
            Aware.setSetting(this, FREQUENCY_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION, 60);
        }
        frequency.setSummary(Aware.getSetting(getApplicationContext(), FREQUENCY_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION) + " seconds");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);

        if (preference.getKey().equals(STATUS_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION)) {
            Aware.setSetting(getApplicationContext(), key, sharedPreferences.getBoolean(key, false));
            status.setChecked(sharedPreferences.getBoolean(key, false));
        }
        if (preference.getKey().equals(FREQUENCY_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION)) {
            Aware.setSetting(getApplicationContext(), key, sharedPreferences.getString(key, "60"));
            frequency.setSummary(Aware.getSetting(getApplicationContext(), FREQUENCY_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION) + " seconds");
        }
        if (Aware.getSetting(this, Settings.STATUS_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION).equals("true")) {
            Aware.startPlugin(getApplicationContext(), "com.aware.plugin.google.activity_recognition");
        } else {
            Aware.stopPlugin(getApplicationContext(), "com.aware.plugin.google.activity_recognition");
        }
    }
}
