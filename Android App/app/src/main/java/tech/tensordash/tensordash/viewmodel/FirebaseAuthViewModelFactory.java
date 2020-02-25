package tech.tensordash.tensordash.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FirebaseAuthViewModelFactory implements ViewModelProvider.Factory {

    private Activity activity;
    private Application application;

    public FirebaseAuthViewModelFactory(Application application, Activity activity){
        this.application = application;
        this.activity = activity;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FirebaseAuthViewModel(application, activity);
    }
}
