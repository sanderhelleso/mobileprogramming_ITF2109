package com.iifym.classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Goal {

    private String uid;
    private double currentWeight;
    private double startWeight;
    private double goalWeight;
    private double activityLvl;
    private int intensityLvl;
    private int estimatedWeeksToReach;
    private Macros macros;

    public Goal() {}

    public Goal(String uid, double currentWeight, double startWeight, double goalWeight,
                double activityLvl, int intensityLvl, int estimatedWeeksToReach, Macros macros)
    {
        this.uid = uid;
        this.currentWeight = currentWeight;
        this.startWeight = startWeight;
        this.goalWeight = goalWeight;
        this.activityLvl = activityLvl;
        this.intensityLvl = intensityLvl;
        this.estimatedWeeksToReach = estimatedWeeksToReach;
        this.macros = macros;
    }

    public String getUid() {
        return uid;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public double getStartWeight() {
        return startWeight;
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    public double getActivityLvl() {
        return activityLvl;
    }

    public int getIntensityLvl() {
        return intensityLvl;
    }

    public int getEstimatedWeeksToReach() {
        return estimatedWeeksToReach;
    }

    public Macros getMacros() {
        return macros;
    }
}
