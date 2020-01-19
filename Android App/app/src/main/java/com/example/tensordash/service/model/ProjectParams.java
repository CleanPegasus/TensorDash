package com.example.tensordash.service.model;

public class ProjectParams {

    private int epoch;
    private double accuracy;
    private double loss;
    private double validationLoss;
    private double valicationAccuracy;

    public ProjectParams(){

    }

    public ProjectParams(int epoch, double accuracy, double loss, double validationLoss, double valicationAccuracy) {
        this.epoch = epoch;
        this.accuracy = accuracy;
        this.loss = loss;
        this.validationLoss = validationLoss;
        this.valicationAccuracy = valicationAccuracy;
    }

    public int getEpoch() {
        return epoch;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getLoss() {
        return loss;
    }

    public double getValidationLoss() {
        return validationLoss;
    }

    public double getValicationAccuracy() {
        return valicationAccuracy;
    }
}
