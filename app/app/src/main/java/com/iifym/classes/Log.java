package com.iifym.classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Log {
    public Date loggedAt;
    public double weight;

    public Log() {}

    public Log(Date loggedAt, double weight) {
        this.loggedAt = loggedAt;
        this.weight = weight;
    }

    public Date getLoggedAt() {
        return loggedAt;
    }

    public double getWeight() {
        return weight;
    }
}
