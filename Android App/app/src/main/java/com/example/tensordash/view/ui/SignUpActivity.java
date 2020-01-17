package com.example.tensordash.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.tensordash.R;
import com.example.tensordash.viewmodel.FirebaseAuthViewModel;
import com.example.tensordash.viewmodel.FirebaseAuthViewModelFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    // TODO: Back icon
    // TODO: Client side verification

    private TextInputEditText emailIdEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        progressBar = findViewById(R.id.progress_bar);

        Button confirmCreateNewAccountButton = findViewById(R.id.button_confirm_create_account);
        confirmCreateNewAccountButton.setOnClickListener(v -> {
            fetchEmailIdAndPassword();
        });

    }
    
    private void fetchEmailIdAndPassword(){
        emailIdEditText = findViewById(R.id.new_email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edittext);
        createNewAccount(emailIdEditText.getText().toString(), passwordEditText.getText().toString());
    }
    
    private void createNewAccount(String emailId, String password){
        showProgressBar();
        OnSuccessListener<AuthResult> onSuccessListener = authResult -> onSuccessfulNewAccountCreation();
        OnFailureListener onFailureListener = this::onFailureOfNewAccountCreation;
        FirebaseAuthViewModel firebaseAuthViewModel = ViewModelProviders.of(SignUpActivity.this, new FirebaseAuthViewModelFactory(this.getApplication(), SignUpActivity.this)).get(FirebaseAuthViewModel.class);
        firebaseAuthViewModel.createNewUser(emailId, password, onSuccessListener, onFailureListener);
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void onSuccessfulNewAccountCreation(){
        hideProgressBar();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void onFailureOfNewAccountCreation(Exception e){
        hideProgressBar();
        Log.e(TAG, "onFailureOfNewAccountCreation: " + e);
        Snackbar.make(findViewById(android.R.id.content), "Unable to create new account. " + e.getMessage(), Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> {
                    createNewAccount(emailIdEditText.getText().toString(), passwordEditText.getText().toString());
                })
                .show();
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }



}
