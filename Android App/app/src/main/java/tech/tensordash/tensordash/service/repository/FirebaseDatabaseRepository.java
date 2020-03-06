package tech.tensordash.tensordash.service.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import tech.tensordash.tensordash.service.model.Project;
import tech.tensordash.tensordash.service.model.ProjectParams;
import tech.tensordash.tensordash.service.model.StatusCode;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FirebaseDatabaseRepository {

    private static final String TAG = "FirebaseDatabaseReposit";

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Project> projectList;
    private MutableLiveData<List<Project>> projectMutableLiveData;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChildEventListener projectsChildEventListener;


    public FirebaseDatabaseRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String databasePath = "user_data/" + firebaseAuth.getUid();
        databaseReference = firebaseDatabase.getReference(databasePath);
        projectList = new ArrayList<>();
        projectMutableLiveData = new MutableLiveData<>();
        setUpListener();
        getProjects();
    }

    private void getProjects() {
        projectList.clear();
        attachListeners();
    }

    public void attachListeners(){
        databaseReference.addChildEventListener(projectsChildEventListener);
    }

    public void detachListeners(){
        if(projectsChildEventListener != null){
            databaseReference.removeEventListener(projectsChildEventListener);
        }
    }

    public MutableLiveData<List<Project>> getAllProjects() {
        return projectMutableLiveData;
    }

    public void refreshProjectList(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        getProjects();
    }

    private int getEpoch(DataSnapshot epochDataSnapShot) {
        int epoch = 0;
        try {
            epoch = Integer.parseInt(epochDataSnapShot.child("Epoch").getValue().toString());
        } catch (Exception ignored) {
        }
        return epoch;
    }

    private double getLoss(DataSnapshot epochDataSnapShot) {
        double loss = 0;
        try {
            loss = Double.parseDouble(epochDataSnapShot.child("Loss").getValue().toString());
        } catch (Exception ignored) {
        }
        return loss;
    }

    private double getAccuracy(DataSnapshot epochDataSnapShot) {
        double accuracy = 0;
        try {
            accuracy = Double.parseDouble(epochDataSnapShot.child("Accuracy").getValue().toString());
        } catch (Exception ignored) {
        }
        return accuracy;
    }

    private double getValidationLoss(DataSnapshot epochDataSnapShot) {
        double validationLoss = 0;
        try {
            validationLoss = Double.parseDouble(epochDataSnapShot.child("Validation Loss").getValue().toString());
        } catch (Exception ignored) {
        }
        return validationLoss;
    }

    private double getValidationAccuracy(DataSnapshot epochDataSnapShot) {
        double validationAccuracy = 0;
        try {
            validationAccuracy = Double.parseDouble(epochDataSnapShot.child("Validation Accuracy").getValue().toString());
        } catch (Exception ignored) {
        }
        return validationAccuracy;
    }

    private void setUpListener() {
        projectsChildEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot projectDataSnapshot, @Nullable String s) {
                Iterator<DataSnapshot> epochLevelIterator = projectDataSnapshot.getChildren().iterator();
                String projectName = projectDataSnapshot.getKey();
                StatusCode status = StatusCode.DEFAULT;
                List<ProjectParams> projectParamsList = new ArrayList<>();
                while (epochLevelIterator.hasNext()) {
                    DataSnapshot epochDataSnapShot = epochLevelIterator.next();
                    if (!epochDataSnapShot.hasChildren()) {
                        if (epochDataSnapShot.getKey().equals("Status")) {
                            status = StatusCode.valueOf(epochDataSnapShot.getValue().toString());
                        }
                        continue;
                    }

                    int epoch = getEpoch(epochDataSnapShot);
                    double loss = getLoss(epochDataSnapShot);
                    double accuracy = getAccuracy(epochDataSnapShot);
                    double validationLoss = getValidationLoss(epochDataSnapShot);
                    double validationAccuracy = getValidationAccuracy(epochDataSnapShot);

                    projectParamsList.add(new ProjectParams(epoch, accuracy, loss, validationLoss, validationAccuracy));
                }
                if(projectParamsList.isEmpty()){
                    projectParamsList.add(new ProjectParams(0,0,0,0,0));
                }
                projectList.add(new Project(projectName, status, projectParamsList));
                projectMutableLiveData.setValue(projectList);
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
    }

    public void deleteProject(String projectName){
        databaseReference.child(projectName).setValue(null);
    }

}
