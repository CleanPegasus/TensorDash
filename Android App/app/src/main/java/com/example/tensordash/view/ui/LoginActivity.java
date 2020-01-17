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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // TODO: Implement client-side verification techniques and errors!
    // TODO: Modify regex so that regex is fetched from the servers.
    String emailIdRegex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$";

    private TextInputEditText emailIdEditText;
    private TextInputEditText passwordEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress_bar);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v ->{
            fetchEmailIdAndPasswordFromEditText();
        });


        Button createNewAccountButton = findViewById(R.id.create_new_key_button);
        createNewAccountButton.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

    }

    private void fetchEmailIdAndPasswordFromEditText(){
        emailIdEditText = findViewById(R.id.new_email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        String emailId = emailIdEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        login(emailId, password);
    }

    private void login(String emailId, String password){
        showProgressBar();
        OnSuccessListener<AuthResult> onSuccessListener = authResult ->  onSuccessfulLogin();
        OnFailureListener onFailureListener = this::onUnsuccessfulLogin;
        FirebaseAuthViewModel firebaseAuthViewModel = ViewModelProviders.of(LoginActivity.this, new FirebaseAuthViewModelFactory(this.getApplication(), LoginActivity.this)).get(FirebaseAuthViewModel.class);
        firebaseAuthViewModel.loginUsingEmailAndPassword(emailId, password, onSuccessListener, onFailureListener);
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void onSuccessfulLogin(){
        hideProgressBar();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void onUnsuccessfulLogin(Exception e){
        hideProgressBar();
        Log.e(TAG, "onUnsuccessfulLogin: " + e);
        Snackbar.make(findViewById(android.R.id.content), "Unable to login. " + e.getMessage(), Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> {
                    login(emailIdEditText.getText().toString(), passwordEditText.getText().toString());
                })
                .show();
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
