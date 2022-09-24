package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import com.polyak.iconswitch.IconSwitch;

public class Settings extends AppCompatActivity {

    Toolbar toolbar;
    IconSwitch darkModeSwitch;
    public static final String DARK_MODE_PREF="DARK_MODE_PREFERENCES";
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        toolbar=findViewById(R.id.toolbar);
        darkModeSwitch=findViewById(R.id.darkmodeSwitch);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        TypedValue typedValue = new TypedValue();

        getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true);
        int colorOnPrimary= typedValue.data;

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_round_arrow_back_24);
        upArrow.setColorFilter(colorOnPrimary, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        toolbar.setNavigationIcon(upArrow);



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (preferences.getString(DARK_MODE_PREF,"light").equals("light"))
        {
            darkModeSwitch.setChecked(IconSwitch.Checked.LEFT);

        }
        else if (preferences.getString(DARK_MODE_PREF,"light").equals("dark"))
        {
            darkModeSwitch.setChecked(IconSwitch.Checked.RIGHT);

        }

        darkModeSwitch.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch (current)
                {
                    case LEFT: //light mode


                        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(DARK_MODE_PREF,"light");

                        editor.apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                        break;

                    case RIGHT: //dark mode

                        SharedPreferences sharedPreferences1= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor1=sharedPreferences1.edit();
                        editor1.putString(DARK_MODE_PREF,"dark");

                        editor1.apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                        break;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}