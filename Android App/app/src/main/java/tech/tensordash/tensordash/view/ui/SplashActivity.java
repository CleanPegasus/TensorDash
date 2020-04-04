package tech.tensordash.tensordash.view.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import tech.tensordash.tensordash.R;
import tech.tensordash.tensordash.viewmodel.FirebaseAuthViewModel;
import tech.tensordash.tensordash.viewmodel.FirebaseAuthViewModelFactory;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
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
