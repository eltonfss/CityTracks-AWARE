package com.aware.plugin.ambient_noise;

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
     * Activate/deactivate plugin
     */
    public static final String STATUS_PLUGIN_AMBIENT_NOISE = "status_plugin_ambient_noise";

    /**
     * How frequently do we sample the microphone (default = 5) in minutes
     */
    public static final String FREQUENCY_PLUGIN_AMBIENT_NOISE = "frequency_plugin_ambient_noise";

    /**
     * For how long we listen (default = 30) in seconds
     */
    public static final String PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE = "plugin_ambient_noise_sample_size";

    /**
     * Silence threshold (default = 50) in dB
     */
    public static final String PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD = "plugin_ambient_noise_silence_threshold";

    private static CheckBoxPreference active;
    private static EditTextPreference frequency, listen, silence;

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

        active = (CheckBoxPreference) findPreference(STATUS_PLUGIN_AMBIENT_NOISE);
        if (Aware.getSetting(getApplicationContext(), STATUS_PLUGIN_AMBIENT_NOISE).length() == 0) {
            Aware.setSetting(getApplicationContext(), STATUS_PLUGIN_AMBIENT_NOISE, true);
        }
        active.setChecked(Aware.getSetting(getApplicationContext(), STATUS_PLUGIN_AMBIENT_NOISE).equals("true"));

        frequency = (EditTextPreference) findPreference(FREQUENCY_PLUGIN_AMBIENT_NOISE);
        if (Aware.getSetting(getApplicationContext(), FREQUENCY_PLUGIN_AMBIENT_NOISE).length() == 0) {
            Aware.setSetting(getApplicationContext(), FREQUENCY_PLUGIN_AMBIENT_NOISE, 5);
        }
        frequency.setSummary("Every " + Aware.getSetting(getApplicationContext(), FREQUENCY_PLUGIN_AMBIENT_NOISE) + " minutes");

        listen = (EditTextPreference) findPreference(PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE);
        if (Aware.getSetting(getApplicationContext(), PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE).length() == 0) {
            Aware.setSetting(getApplicationContext(), PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE, 30);
        }
        listen.setSummary("Listen " + Aware.getSetting(getApplicationContext(), PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE) + " second(s)");

        silence = (EditTextPreference) findPreference(PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD);
        if (Aware.getSetting(getApplicationContext(), PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD).length() == 0) {
            Aware.setSetting(getApplicationContext(), PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD, 50);
        }
        silence.setSummary("Silent below " + Aware.getSetting(getApplicationContext(), PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD) + "dB");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference.getKey().equals(FREQUENCY_PLUGIN_AMBIENT_NOISE)) {
            Aware.setSetting(getApplicationContext(), key, sharedPreferences.getString(key, "5"));
            frequency.setSummary("Every " + Aware.getSetting(getApplicationContext(), FREQUENCY_PLUGIN_AMBIENT_NOISE) + " minutes");
        }
        if (preference.getKey().equals(PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE)) {
            Aware.setSetting(getApplicationContext(), key, sharedPreferences.getString(key, "30"));
            listen.setSummary("Listen " + Aware.getSetting(getApplicationContext(), PLUGIN_AMBIENT_NOISE_SAMPLE_SIZE) + " second(s)");
        }
        if (preference.getKey().equals(PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD)) {
            Aware.setSetting(getApplicationContext(), key, sharedPreferences.getString(key, "50"));
            silence.setSummary("Silent below " + Aware.getSetting(getApplicationContext(), PLUGIN_AMBIENT_NOISE_SILENCE_THRESHOLD) + "dB");
        }
        if (preference.getKey().equals(STATUS_PLUGIN_AMBIENT_NOISE)) {
            Aware.setSetting(getApplicationContext(), key, sharedPreferences.getBoolean(key, false));
            active.setChecked(sharedPreferences.getBoolean(key, false));
        }
        if (Aware.getSetting(this, STATUS_PLUGIN_AMBIENT_NOISE).equals("true")) {
            Aware.startPlugin(getApplicationContext(), "com.aware.plugin.ambient_noise");
        } else {
            Aware.stopPlugin(getApplicationContext(), "com.aware.plugin.ambient_noise");
        }
    }
}
