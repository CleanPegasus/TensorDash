package com.example.tensordash.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
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

public class ForgotPasswordActivity extends AppCompatActivity {

    // TODO: Add a back button
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        progressBar = findViewById(R.id.forgot_password_progress_bar);
        Button resetPasswordButton = findViewById(R.id.reset_password_button);

        resetPasswordButton.setOnClickListener(v -> resetPassword());

        ImageView imageView = findViewById(R.id.back_image_view_forgot);
        imageView.setOnClickListener(v -> onBackPressed());
    }

    private void resetPassword() {
        TextInputEditText emailInputEditText = findViewById(R.id.forgot_email_edittext);
        String emailId = emailInputEditText.getText().toString();
        if(emailId.equals("")){
            Snackbar.make(findViewById(android.R.id.content), "Email is empty", Snackbar.LENGTH_SHORT).show();
            return;
        }
        showProgressBar();
        OnSuccessListener<Void> onSuccessListener = aVoid -> onSuccessfulReset();

        OnFailureListener onFailureListener = this::onUnsuccessfulReset;

        FirebaseAuthViewModel firebaseAuthViewModel = ViewModelProviders.of(ForgotPasswordActivity.this, new FirebaseAuthViewModelFactory(this.getApplication(), ForgotPasswordActivity.this)).get(FirebaseAuthViewModel.class);
        firebaseAuthViewModel.resetPassword(emailId, onSuccessListener, onFailureListener);
    }

    private void onSuccessfulReset() {
        hideProgressBar();
        Snackbar.make(findViewById(android.R.id.content), "Reset link sent to mail successfully", Snackbar.LENGTH_SHORT).show();
    }

    private void onUnsuccessfulReset(Exception e) {
        hideProgressBar();
        Snackbar.make(findViewById(android.R.id.content), "Error occurred. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
