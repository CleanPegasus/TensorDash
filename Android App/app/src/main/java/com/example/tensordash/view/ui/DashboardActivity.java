package com.example.tensordash.view.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.tensordash.R;
import com.example.tensordash.service.repository.FirebaseDatabaseRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private FirebaseDatabaseRepository firebaseDatabaseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseDatabaseRepository = new FirebaseDatabaseRepository();
        firebaseDatabaseRepository.getAllProjects().observe(this, projects -> {
            Log.d(TAG, "onCreate: "  + projects.size());
        });
    }
}
