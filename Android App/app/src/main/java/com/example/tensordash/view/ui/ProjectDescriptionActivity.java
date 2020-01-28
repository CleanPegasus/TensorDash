package com.example.tensordash.view.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tensordash.R;
import com.example.tensordash.service.model.Project;
import com.example.tensordash.service.model.ProjectParams;
import com.example.tensordash.viewmodel.FirebaseDatabaseViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ProjectDescriptionActivity extends AppCompatActivity {

    private FirebaseDatabaseViewModel firebaseDatabaseViewModel;
    private TextView projectNameTextView;
    private TextView epochTextView;
    private TextView accuracyTextView;
    private TextView lossTextView;
    private TextView validationAccuracyTextView;
    private TextView validationLossTextView;
    private LineChart lineChartLoss;
    private LineChart lineChartAccuracy;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_description);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Project Details");

        projectNameTextView = findViewById(R.id.project_name_description_textview);
        epochTextView = findViewById(R.id.epoch_project_description_textview);
        accuracyTextView = findViewById(R.id.accuracy_project_description_textview);
        lossTextView = findViewById(R.id.loss_project_description_textview);
        validationAccuracyTextView = findViewById(R.id.validation_accuracy_project_description_textview);
        validationLossTextView = findViewById(R.id.validation_loss_project_description_textview);
        lineChartLoss = findViewById(R.id.chart_view_loss);
        lineChartAccuracy = findViewById(R.id.chart_view_accuracy);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_description);

        loadActivity();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            firebaseDatabaseViewModel.refreshProjectList(swipeRefreshLayout);
            loadActivity();
        });


    }

    public void loadActivity() {

        firebaseDatabaseViewModel = ViewModelProviders.of(ProjectDescriptionActivity.this).get(FirebaseDatabaseViewModel.class);
        String projectName = getIntent().getStringExtra("project_name");
        firebaseDatabaseViewModel.getAllProjects().observe(this, projects -> {
            for (Project project : projects) {
                if (projectName.equals(project.getProjectName())) {
                    setValues(project);
                    break;
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setValues(Project project) {
        projectNameTextView.setText(project.getProjectName());
        epochTextView.setText(String.valueOf(project.getLatestEpoch()));
        accuracyTextView.setText(String.valueOf(project.getLatestAccuracy()));
        lossTextView.setText(String.valueOf(project.getLatestLoss()));
        validationAccuracyTextView.setText(String.valueOf(project.getLatestValidationAccuracy()));
        validationLossTextView.setText(String.valueOf(project.getLatestValidationLoss()));
        setUpChart(project.getProjectParamsList());
    }

    private void setUpChart(List<ProjectParams> projectParamsList) {

        final int[] colors = new int[]{
                ColorTemplate.VORDIPLOM_COLORS[0],
                ColorTemplate.VORDIPLOM_COLORS[1],
                ColorTemplate.VORDIPLOM_COLORS[2],
                ColorTemplate.VORDIPLOM_COLORS[3],
        };

        ArrayList<Entry> lossEntries = new ArrayList<>();
        ArrayList<Entry> accuracyEntries = new ArrayList<>();
        for (ProjectParams projectParams : projectParamsList) {
            lossEntries.add(new Entry(projectParams.getEpoch(), (float) projectParams.getLoss()));
            accuracyEntries.add(new Entry(projectParams.getEpoch(), (float) projectParams.getAccuracy()));
        }

        float textSize = 9;

        LineDataSet lossDataset = new LineDataSet(lossEntries, "Loss");
        lossDataset.setColor(colors[0]);
        lossDataset.setValueTextColor(colors[0]);
        lossDataset.setValueTextSize(textSize);

        LineDataSet accuracyDataset = new LineDataSet(accuracyEntries, "Accuracy");
        accuracyDataset.setColor(colors[1]);
        accuracyDataset.setValueTextColor(colors[1]);
        accuracyDataset.setValueTextSize(textSize);


        XAxis xAxis = lineChartLoss.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxisRight = lineChartLoss.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxisLeft = lineChartLoss.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setGranularity(1f);


        LineData data = new LineData(lossDataset);

        lineChartLoss.getLegend().setTextColor(Color.WHITE);
        lineChartLoss.getDescription().setEnabled(false);
        lineChartLoss.setData(data);
        lineChartLoss.invalidate();

        XAxis lineChartAccuracyXAxis = lineChartAccuracy.getXAxis();
        lineChartAccuracyXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChartAccuracyXAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        lineChartAccuracyXAxis.setTextColor(Color.WHITE);

        YAxis lineChartAccuracyAxisRight = lineChartAccuracy.getAxisRight();
        lineChartAccuracyAxisRight.setEnabled(false);

        YAxis lineChartAccuracyAxisLeft = lineChartAccuracy.getAxisLeft();
        lineChartAccuracyAxisLeft.setTextColor(Color.WHITE);
        lineChartAccuracyAxisLeft.setGranularity(1f);


        LineData dataset = new LineData(accuracyDataset);
        lineChartAccuracy.getLegend().setTextColor(Color.WHITE);
        lineChartAccuracy.getDescription().setEnabled(false);
        lineChartAccuracy.setData(dataset);
        lineChartAccuracy.invalidate();

    }


}
