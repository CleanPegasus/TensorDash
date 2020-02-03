package com.example.tensordash.view.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tensordash.R;
import com.example.tensordash.view.adapter.ProjectAdapter;
import com.example.tensordash.viewmodel.FirebaseAuthViewModel;
import com.example.tensordash.viewmodel.FirebaseAuthViewModelFactory;
import com.example.tensordash.viewmodel.FirebaseDatabaseViewModel;
import com.google.firebase.messaging.FirebaseMessaging;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    FirebaseDatabaseViewModel databaseViewModel;
    FirebaseAuthViewModel firebaseAuthViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final ProjectAdapter projectAdapter = new ProjectAdapter();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(projectAdapter);

        firebaseAuthViewModel = ViewModelProviders.of(DashboardActivity.this, new FirebaseAuthViewModelFactory(getApplication(), DashboardActivity.this)).get(FirebaseAuthViewModel.class);

        databaseViewModel = ViewModelProviders.of(DashboardActivity.this).get(FirebaseDatabaseViewModel.class);
        databaseViewModel.getAllProjects().observe(this, projects -> projectAdapter.submitList(projects));

        projectAdapter.setOnItemClickListener(project -> {
            Intent intent = new Intent(DashboardActivity.this, ProjectDescriptionActivity.class);
            intent.putExtra("project_name", project.getProjectName());
            startActivity(intent);
        });

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            databaseViewModel.refreshProjectList(swipeRefreshLayout);
            databaseViewModel.getAllProjects().observe(this, projects -> {
                projectAdapter.submitList(projects);
                projectAdapter.notifyDataSetChanged();
            });

        });

        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_signout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
            builder.setTitle("Sign out?")
                    .setMessage("Do you want to sign out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        signOut();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                    })
                    .create()
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        // TODO: Add a listener
        firebaseAuthViewModel.signOut();
        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        finishAffinity();
    }

}
