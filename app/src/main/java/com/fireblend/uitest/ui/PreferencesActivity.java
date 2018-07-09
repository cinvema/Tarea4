package com.fireblend.uitest.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.fireblend.uitest.R;

public class PreferencesActivity extends Activity {

    private static int prefs = R.layout.app_preferences;

    //private Context contexto;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }


    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        //TextView tvNombre;
        Button botonEliminar;
        @SuppressLint("ResourceType")
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(PreferencesActivity.prefs);


            onSharedPreferenceChanged(null, "");

        }

        @Override
        public void onResume() {
            super.onResume();
            // Set up a listener whenever a key changes
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            // Set up a listener whenever a key changes
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            String ver_key = key;
            Preference pref = findPreference(key);


            if (pref instanceof CheckBoxPreference) {
                if (((CheckBoxPreference) pref).isChecked())
                    pref.setSummary("On");
                else
                    pref.setSummary("Off");

            }



        }

    }


}
