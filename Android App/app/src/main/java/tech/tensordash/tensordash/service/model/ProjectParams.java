package tech.tensordash.tensordash.service.model;

public class ProjectParams {

    private int epoch;
    private double accuracy;
    private double loss;
    private double validationLoss;
    private double validationAccuracy;

    public ProjectParams(){

    }

    public ProjectParams(int epoch, double accuracy, double loss, double validationLoss, double validationAccuracy) {
        this.epoch = epoch;
        this.accuracy = accuracy;
        this.loss = loss;
        this.validationLoss = validationLoss;
        this.validationAccuracy = validationAccuracy;
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

    public double getValidationAccuracy() {
        return validationAccuracy;
    }
}
