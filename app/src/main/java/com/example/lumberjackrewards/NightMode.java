package com.example.lumberjackrewards;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceManager;

import java.util.Set;

public class NightMode extends Application {
  private static final String IS_DARK = "IS_DARK";
    private static String MY_PREFS = "switch_prefs";
    @Override
  protected void attachBaseContext(Context baseContext) {
        super.attachBaseContext(baseContext);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        boolean isDark = prefs.getBoolean(IS_DARK, false);

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}
