package com.example.dalangapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.dalangapp.content.detail.WayangDetailActivity


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val langPref: Preference? = findPreference("lang")
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            langPref?.intent = intent

            val btnLogout: Preference? = findPreference("endsession")
            val toLogin = Intent(activity, WayangDetailActivity::class.java)
            btnLogout?.setOnPreferenceClickListener {
                startActivity(toLogin)
                true
            }
        }

    }
}