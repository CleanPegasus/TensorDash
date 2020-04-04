package tech.tensordash.tensordash.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import tech.tensordash.tensordash.service.repository.FirebaseAuthRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

public class FirebaseAuthViewModel extends AndroidViewModel {

    private FirebaseAuthRepository firebaseAuthRepository;
    private Activity activity;

    public FirebaseAuthViewModel(@NonNull Application application, Activity activity) {
        super(application);
        firebaseAuthRepository = new FirebaseAuthRepository(activity);
    }

    public boolean checkIfUserIsSignedIn(){
        return firebaseAuthRepository.checkIfUserIsSignedIn();
    }

    public void loginUsingEmailAndPassword(String emailId, String password, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener){
        firebaseAuthRepository.loginUsingEmailAndPassword(emailId, password, onSuccessListener, onFailureListener);
    }

    public void createNewUser(String emailId, String password, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener){
        firebaseAuthRepository.createNewUser(emailId, password, onSuccessListener, onFailureListener);
    }

    public void resetPassword(String emailId, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener){
        firebaseAuthRepository.resetPassword(emailId, onSuccessListener, onFailureListener);
    }

        public void signOut(){
        firebaseAuthRepository.signOut();
    }
}
