package com.example.tensordash.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tensordash.R;
import com.example.tensordash.service.model.Project;

import java.util.Collections;

public class ProjectAdapter extends ListAdapter<Project, ProjectAdapter.ProjectHolder> {


    private OnItemClickListener listener;

    public ProjectAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<Project> DIFF_CALLBACK = new DiffUtil.ItemCallback<Project>() {
        @Override
        public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getProjectName().equals(newItem.getProjectName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return checkIfContentsAreSame(oldItem, newItem);
        }
    };

    private static boolean checkIfContentsAreSame(Project oldItem, Project newItem){
        if(!oldItem.getProjectName().equals(newItem.getProjectName())){
            return false;
        }else{
            return (oldItem.getProjectParamsList().equals(newItem.getProjectParamsList()));
        }
    }


    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_list_item, parent,false);

        return new ProjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.ProjectHolder holder, int position) {
        Project project = getItem(position);

        holder.projectName.setText(project.getProjectName());
        holder.epochValue.setText(String.valueOf(project.getProjectParamsList().get(project.getProjectParamsList().size() - 1).getEpoch()));
        holder.lossValue.setText(String.valueOf(project.getProjectParamsList().get(project.getProjectParamsList().size() - 1).getLoss()));
        holder.accuracyValue.setText(String.valueOf(project.getProjectParamsList().get(project.getProjectParamsList().size() - 1).getAccuracy()));

    }

    class ProjectHolder extends RecyclerView.ViewHolder {
        private TextView projectName;
        private TextView epochValue;
        private TextView lossValue;
        private TextView accuracyValue;

        ProjectHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.project_name_textview);
            epochValue = itemView.findViewById(R.id.epoch_textview);
            lossValue = itemView.findViewById(R.id.loss_textview);
            accuracyValue = itemView.findViewById(R.id.accuracy_textview);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Project project);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}


