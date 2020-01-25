package com.example.tensordash.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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
    private LineChart lineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_description);
        projectNameTextView = findViewById(R.id.project_name_description_textview);
        epochTextView = findViewById(R.id.epoch_project_description_textview);
        accuracyTextView = findViewById(R.id.accuracy_project_description_textview);
        lossTextView = findViewById(R.id.loss_project_description_textview);
        validationAccuracyTextView = findViewById(R.id.validation_accuracy_project_description_textview);
        validationLossTextView = findViewById(R.id.validation_loss_project_description_textview);
        lineChart = findViewById(R.id.chart_view);


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

        final int[] colors = new int[] {
                ColorTemplate.VORDIPLOM_COLORS[0],
                ColorTemplate.VORDIPLOM_COLORS[1],
                ColorTemplate.VORDIPLOM_COLORS[2],
                ColorTemplate.VORDIPLOM_COLORS[3],
        };

        ArrayList<Entry> lossEntries = new ArrayList<>();
        ArrayList<Entry> accuracyEntries = new ArrayList<>();
        ArrayList<Entry> valLossEntries = new ArrayList<>();
        ArrayList<Entry> valAccEntries = new ArrayList<>();
        for (ProjectParams projectParams : projectParamsList) {
            lossEntries.add(new Entry(projectParams.getEpoch(), (float)projectParams.getLoss()));
            accuracyEntries.add(new Entry(projectParams.getEpoch(), (float)projectParams.getAccuracy()));
            valLossEntries.add(new Entry(projectParams.getEpoch(), (float)projectParams.getValidationLoss()));
            valAccEntries.add(new Entry(projectParams.getEpoch(), (float)projectParams.getValicationAccuracy()));
        }

        ArrayList<ILineDataSet> lines = new ArrayList<>();

        float textSize = 9;

        LineDataSet lossDataset = new LineDataSet(lossEntries, "Loss");
        lossDataset.setColor(colors[0]);
        lossDataset.setValueTextColor(colors[0]);
        lossDataset.setValueTextSize(textSize);
        lines.add(lossDataset);

        LineDataSet accuracyDataset = new LineDataSet(accuracyEntries, "Accuracy");
        accuracyDataset.setColor(colors[1]);
        accuracyDataset.setValueTextColor(colors[1]);
        accuracyDataset.setValueTextSize(textSize);
        lines.add(accuracyDataset);

//        LineDataSet valLossDataset = new LineDataSet(accuracyEntries, "Validation Loss");
//        valLossDataset.setColor(colors[2]);
//        valLossDataset.setValueTextColor(colors[2]);
//        lines.add(valLossDataset);
//
//        LineDataSet valAccDataset = new LineDataSet(accuracyEntries, "Validation Accuracy");
//        valAccDataset.setColor(colors[3]);
//        valAccDataset.setValueTextColor(colors[3]);
//        lines.add(valAccDataset);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setGranularity(1f);


        LineData data = new LineData(lines);

        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.setData(data);
        lineChart.invalidate();

    }


}
