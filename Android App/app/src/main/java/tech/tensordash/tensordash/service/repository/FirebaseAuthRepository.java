package tech.tensordash.tensordash.service.repository;

import android.app.Activity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

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
        FirebaseMessaging.getInstance().unsubscribeFromTopic(mAuth.getUid());
        FirebaseAuth.getInstance().signOut();
    }
}
