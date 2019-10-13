package com.iifym.classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@IgnoreExtraProperties
public class WeightLogs {
    public Date lastLogged;
    public List<Log> logs = new ArrayList<>();
    public List<Double> averageWeights = new ArrayList<>();

    public WeightLogs() {}

    public WeightLogs(Date lastLogged, List<Log> logs) {
        this.lastLogged = lastLogged;
        this.logs = logs;
    }

    public boolean canAddNewLog() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(this.lastLogged);
        cal2.setTime(new Date());

        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        boolean sameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

        return !(sameDay && sameYear);
    }

    public Date getLastLogged() {
        return lastLogged;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public List<Double> getAverageWeights() {
        return averageWeights;
    }

    public void addLog(Log log) {
        this.logs.add(log);
    }

    public void setLastLogged() {
        this.lastLogged = new Date();
    }
}
