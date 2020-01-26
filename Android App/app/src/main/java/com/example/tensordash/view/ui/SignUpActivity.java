package com.example.tensordash.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.tensordash.R;
import com.example.tensordash.viewmodel.FirebaseAuthViewModel;
import com.example.tensordash.viewmodel.FirebaseAuthViewModelFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    // TODO: Back icon
    // TODO: Client side verification

    private TextInputEditText emailIdEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;
    private TextInputLayout emailIdTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private TextInputLayout confirmPasswordTextInputLayout;
    private ProgressBar progressBar;
    private ImageView backButtonImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        progressBar = findViewById(R.id.progress_bar);
        emailIdEditText = findViewById(R.id.new_email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edittext);
        emailIdTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        confirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout);
        backButtonImageView = findViewById(R.id.back_image_view);

        Button confirmCreateNewAccountButton = findViewById(R.id.button_confirm_create_account);
        confirmCreateNewAccountButton.setOnClickListener(v -> {
            fetchEmailIdAndPassword();
        });

        emailIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                checkIfEmailAddressIsCorrect();
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() < 8){
                    passwordTextInputLayout.setError("Minimum length of password should be 8");
                }else{
                    passwordTextInputLayout.setError(null);
                }
            }
        });

        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() < 8){
                    confirmPasswordTextInputLayout.setError("Minimum length of password should be 8");
                }else if(!s.toString().equals(passwordEditText.getText().toString())){
                    confirmPasswordTextInputLayout.setError("Passwords do not match");
                }else{
                    confirmPasswordTextInputLayout.setError(null);
                }
            }
        });

        backButtonImageView.setOnClickListener(v -> onBackPressed());

    }
    
    private void fetchEmailIdAndPassword(){
        // TODO: Check if the app crashes if you enter nothing...
        if (isErrorInInput()){
            return;
        }
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
        Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
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

    private void checkIfEmailAddressIsCorrect(){
        // TODO: Modify regex so that regex is fetched from the servers.

        if(emailIdEditText.getText().toString().equals("")){
            raiseIncorrectEmailError();
        }

        String emailIdRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        String email = emailIdEditText.getText().toString();
        if(!email.matches(emailIdRegex)){
            raiseIncorrectEmailError();
        }else{
            emailIdTextInputLayout.setError(null);
        }

    }

    public void raiseIncorrectEmailError(){
        emailIdTextInputLayout.setError("Invalid email address");
    }

    private boolean isErrorInInput(){
        if (passwordEditText.getText().toString().length() < 8){
            Snackbar.make(findViewById(android.R.id.content), "Resolve all errors", Snackbar.LENGTH_SHORT)
                    .show();
            return true;
        }
        if (!passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())){
            confirmPasswordTextInputLayout.setError("Passwords do not match");
            return true;
        }
        return false;
    }


}
