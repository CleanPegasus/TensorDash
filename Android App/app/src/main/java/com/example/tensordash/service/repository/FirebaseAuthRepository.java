package com.example.tensordash.service.repository;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tensordash.view.ui.MainActivity;
import com.example.tensordash.view.ui.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class FirebaseAuthRepository {
    private static final String TAG = "FirebaseAuthRepository";

    private FirebaseAuth mAuth;
    private Activity activity;

    public FirebaseAuthRepository(Activity activity){
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
    }

    public boolean checkIfUserIsSignedIn(){
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }

    public void createNewUser(String emailId, String password, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener){
        mAuth.createUserWithEmailAndPassword(emailId, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void loginUsingEmailAndPassword(String emailId, String password, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener){
        mAuth.signInWithEmailAndPassword(emailId, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void resetPassword(String emailId, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener){
        mAuth.sendPasswordResetEmail(emailId)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
}
