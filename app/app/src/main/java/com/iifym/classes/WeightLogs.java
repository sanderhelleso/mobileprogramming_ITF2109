package com.iifym.classes;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class WeightLogs {
    private final static int ERA_LENGTH = 7;

    private Date lastLogged;
    private List<Log> logs = new ArrayList<>();
    private List<Double> averageWeights = new ArrayList<>();
    private String uid;

    public WeightLogs() {}

    public WeightLogs(Date lastLogged, List<Log> logs, String uid) {
        this.lastLogged = lastLogged;
        this.logs = logs;
        this.uid = uid;
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

    public long daysSinceLastLogged() {
        if (lastLogged == null) return 1;

        return (int)((lastLogged.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
    }

    public static Double calculateAvgWeightFromLastEra(List<Log> logs) {
        double avg = 0;
        int logSize = logs.size();

        for (int i = logSize - ERA_LENGTH; i < logSize; i++) {
            avg += logs.get(i).getWeight();
        }

        return avg / ERA_LENGTH;
    }

    public static void addLog(final Log log) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference weightLogsRef = db.collection("weightLogs");
        Query query = weightLogsRef.whereEqualTo("uid", User.getUID());

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            // create document if not present for user
                            if (task.getResult().isEmpty()) {
                                WeightLogs weightLogs = new WeightLogs();
                                weightLogs.setLastLogged(log.getLoggedAt());
                                weightLogs.getLogs().add(log);
                                weightLogs.setUid(User.getUID());

                                weightLogsRef.add(weightLogs);
                                return;
                            }

                            QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                            WeightLogs weightLogs = document.toObject(WeightLogs.class);

                            List<Log> logs = weightLogs.getLogs();
                            logs.add(log);

                            Map<Object, Object> propMap = new HashMap<>();
                            propMap.put("lastLogged", log.getLoggedAt());
                            propMap.put("logs", logs);

                            // recalculate if current era is over
                            if (logs.size() % ERA_LENGTH == 0) {
                                double avgWeight = User.recalculate(weightLogs);
                                List<Double> avgWeights = weightLogs.getAverageWeights();

                                avgWeights.add(avgWeight);
                                propMap.put("averageWeights", avgWeights);

                                User.updateGoal();
                            }

                            weightLogsRef.document(document.getId()).set(propMap, SetOptions.merge());
                        }
                    }
                });
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

    public void setLastLogged(Date date) {
        this.lastLogged = date;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
