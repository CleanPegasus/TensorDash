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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tensordash.R;
import com.example.tensordash.viewmodel.FirebaseAuthViewModel;
import com.example.tensordash.viewmodel.FirebaseAuthViewModelFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // TODO: Implement client-side verification techniques and errors!


    private TextInputEditText emailIdEditText;
    private TextInputEditText passwordEditText;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private ProgressBar progressBar;
    private TextView forgotPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress_bar);
        emailIdEditText = findViewById(R.id.new_email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        forgotPasswordTextView = findViewById(R.id.forgot_password_textview);


        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            fetchEmailIdAndPasswordFromEditText();
        });


        Button createNewAccountButton = findViewById(R.id.create_new_key_button);
        createNewAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });


        emailIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIfEmailAddressIsCorrect();
            }
        });

        forgotPasswordTextView.setOnClickListener(v -> onForgetPassword());

    }

    private void fetchEmailIdAndPasswordFromEditText() {
        String emailId = emailIdEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        login(emailId, password);
    }

    private void login(String emailId, String password) {
        showProgressBar();
        OnSuccessListener<AuthResult> onSuccessListener = authResult -> onSuccessfulLogin();
        OnFailureListener onFailureListener = this::onUnsuccessfulLogin;
        FirebaseAuthViewModel firebaseAuthViewModel = ViewModelProviders.of(LoginActivity.this, new FirebaseAuthViewModelFactory(this.getApplication(), LoginActivity.this)).get(FirebaseAuthViewModel.class);
        firebaseAuthViewModel.loginUsingEmailAndPassword(emailId, password, onSuccessListener, onFailureListener);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void onSuccessfulLogin() {
        hideProgressBar();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void onUnsuccessfulLogin(Exception e) {
        hideProgressBar();
        Log.e(TAG, "onUnsuccessfulLogin: " + e);
        Snackbar.make(findViewById(android.R.id.content), "Unable to login. " + e.getMessage(), Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> {
                    login(emailIdEditText.getText().toString(), passwordEditText.getText().toString());
                })
                .show();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void checkIfEmailAddressIsCorrect() {
        // TODO: Modify regex so that regex is fetched from the servers.

        if (emailIdEditText.getText().toString().equals("")) {
            raiseIncorrectEmailError();
        }

        String emailIdRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        String email = emailIdEditText.getText().toString();
        if (!email.matches(emailIdRegex)) {
            raiseIncorrectEmailError();
        } else {
            emailTextInputLayout.setError(null);
        }

    }

    public void raiseIncorrectEmailError() {
        emailTextInputLayout.setError("Invalid email address");
    }

    private void onForgetPassword() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

}
