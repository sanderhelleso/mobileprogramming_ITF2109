package com.iifym.classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Goal {

    public String uid;
    public int currentWeight;
    public int startWeight;
    public int goalWeight;
    public double activityLvl;
    public int intensityLvl;
    public int estimatedWeeksToReach;

    public Goal() {}

    public Goal(String uid, int currentWeight, int startWeight, int goalWeight, double activityLvl, int intensityLvl, int estimatedWeeksToReach) {
        this.uid = uid;
        this.currentWeight = currentWeight;
        this.startWeight = startWeight;
        this.goalWeight = goalWeight;
        this.activityLvl = activityLvl;
        this.intensityLvl = intensityLvl;
        this.estimatedWeeksToReach = estimatedWeeksToReach;
    }
}