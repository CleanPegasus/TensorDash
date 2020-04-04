package tech.tensordash.tensordash.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import tech.tensordash.tensordash.service.model.Project;
import tech.tensordash.tensordash.service.repository.FirebaseDatabaseRepository;

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

    public void detachListeners(){
        firebaseDatabaseRepository.detachListeners();
    }

    public void deleteProject(String projectName){
        firebaseDatabaseRepository.deleteProject(projectName);
    }


}
