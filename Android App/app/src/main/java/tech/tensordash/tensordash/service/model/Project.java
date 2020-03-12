package tech.tensordash.tensordash.service.model;

import java.util.List;

public class Project {
    private String projectName;
    private List<ProjectParams> projectParamsList;
    private StatusCode statusCode;


    public Project(){
    }

    public Project(String projectName, StatusCode statusCode, List<ProjectParams> projectParamsList) {
        this.projectName = projectName;
        this.projectParamsList = projectParamsList;
        this.statusCode = statusCode;
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

    public int getLatestEpoch(){
        return projectParamsList.get(projectParamsList.size() - 1).getEpoch();
    }

    public double getLatestLoss(){
        return projectParamsList.get(projectParamsList.size() - 1).getLoss();
    }

    public double getLatestAccuracy(){
        return projectParamsList.get(projectParamsList.size() - 1).getAccuracy();
    }

    public double getLatestValidationAccuracy(){
        return projectParamsList.get(projectParamsList.size() - 1).getValidationAccuracy();
    }

    public double getLatestValidationLoss(){
        return projectParamsList.get(projectParamsList.size() - 1).getValidationLoss();
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
