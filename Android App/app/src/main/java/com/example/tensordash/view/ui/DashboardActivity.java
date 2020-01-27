package com.example.tensordash.view.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.tensordash.R;
import com.example.tensordash.service.model.Project;
import com.example.tensordash.service.repository.FirebaseDatabaseRepository;
import com.example.tensordash.view.adapter.ProjectAdapter;
import com.example.tensordash.viewmodel.FirebaseDatabaseViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    FirebaseDatabaseViewModel databaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final ProjectAdapter projectAdapter = new ProjectAdapter();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(projectAdapter);

        databaseViewModel = ViewModelProviders.of(DashboardActivity.this).get(FirebaseDatabaseViewModel.class);
        databaseViewModel.getAllProjects().observe(this, projects -> projectAdapter.submitList(projects));

        projectAdapter.setOnItemClickListener(project -> {
            Intent intent = new Intent(DashboardActivity.this, ProjectDescriptionActivity.class);
            intent.putExtra("project_name", project.getProjectName());
            startActivity(intent);
        });
    }
}
