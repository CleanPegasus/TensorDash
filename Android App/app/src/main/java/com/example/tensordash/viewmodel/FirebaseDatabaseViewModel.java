package com.example.tensordash.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tensordash.service.model.Project;
import com.example.tensordash.service.repository.FirebaseDatabaseRepository;

import java.util.List;

public class FirebaseDatabaseViewModel extends AndroidViewModel {

    private FirebaseDatabaseRepository firebaseDatabaseRepository;
    private MutableLiveData<List<Project>> projectList;

    public FirebaseDatabaseViewModel(@NonNull Application application) {
        super(application);
        firebaseDatabaseRepository = new FirebaseDatabaseRepository();
        projectList = firebaseDatabaseRepository.getAllProjects();
    }

    public MutableLiveData<List<Project>> getAllProjects(){
        return projectList;
    }

    public void refreshProjectList(SwipeRefreshLayout swipeRefreshLayout){
        firebaseDatabaseRepository.refreshProjectList(swipeRefreshLayout);
    }


}
