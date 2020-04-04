package tech.tensordash.tensordash;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
