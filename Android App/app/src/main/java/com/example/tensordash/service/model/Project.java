package com.example.tensordash.service.model;

import java.util.List;

public class Project {
    private String projectName;
    private List<ProjectParams> projectParamsList;

    public Project(){
    }

    public Project(String projectName, List<ProjectParams> projectParamsList) {
        this.projectName = projectName;
        this.projectParamsList = projectParamsList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectParams> getProjectParamsList() {
        return projectParamsList;
    }

    public void setProjectParamsList(List<ProjectParams> projectParamsList) {
        this.projectParamsList = projectParamsList;
    }
}
