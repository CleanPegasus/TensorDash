package com.example.tensordash.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.tensordash.R;
import com.example.tensordash.viewmodel.FirebaseAuthViewModel;
import com.example.tensordash.viewmodel.FirebaseAuthViewModelFactory;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            FirebaseAuthViewModel firebaseAuthViewModel = ViewModelProviders.of(SplashActivity.this, new FirebaseAuthViewModelFactory(this.getApplication(), SplashActivity.this)).get(FirebaseAuthViewModel.class);
            if (firebaseAuthViewModel.checkIfUserIsSignedIn()){
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
            }else{
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finishAffinity();
        }, 1500);

    }

    // TODO: Add a condition for onPause(), so when the user exits the application from SplashActivity, it kills the Handler
}
