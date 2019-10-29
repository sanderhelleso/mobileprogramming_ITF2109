package com.iifym.classes;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.iifym.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class User {
    private static String gender;
    private static Date birthday;
    private static int height;
    private static double currentWeight;
    private static double goalWeight;
    private static double activityLvl;
    private static int intensityLvl;
    private static int workoutsPerWeek;
    private static int minutesPerWorkout;
    private static boolean hasGoal;
    private static Goal goal;
    private static WeightLogs weightLogs;

    private static final SimpleDateFormat D_FORMAT = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

    public static int getAge() {
        Calendar present = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.setTime(birthday);

        int years = 0;
        while (past.before(present)) {
            past.add(Calendar.YEAR, 1);
            years++;
        }

        return years;
    }

    public static void saveProfile() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference profilesRef = db.collection("profiles");
        Profile profile = new Profile(getUID(), gender, birthday, height, false);
        profilesRef.add(profile);
    }

    public static void saveGoal(Activity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = getUID();
        CollectionReference goalsRef = db.collection("goals");

        // add the new created goal
        Goal goal = new Goal(uid, currentWeight, currentWeight, goalWeight, activityLvl,
                intensityLvl, calculateWeeksToReachGoal(), calculateMacros());

        goalsRef.add(goal);
        User.goal = goal;

        if (!hasGoal) {
            setProfileHasGoal(db, uid);
        }

        IntentSelector.replaceActivity(new Intent(activity, HomeActivity.class), activity);
    }

    public static void updateGoal() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference goalsRef = db.collection("goals");
        Query query = goalsRef.whereEqualTo("uid", getUID());

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);

                            Map<Object, Object> propMap = new HashMap<>();
                            propMap.put("macros", calculateMacros());
                            propMap.put("estimatedWeeksToReach", calculateWeeksToReachGoal());
                            propMap.put("currentWeight", getCurrentWeight());

                            goalsRef.document(document.getId()).set(propMap, SetOptions.merge());
                        }
                    }
                });
    }

    private static void setProfileHasGoal(FirebaseFirestore db, String uid) {
        final CollectionReference profilesRef = db.collection("profiles");
        Query query = profilesRef.whereEqualTo("uid", uid).limit(1);

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);

                            Profile profile = document.toObject(Profile.class);
                            profile.setHasGoal();

                            profilesRef.document(document.getId()).set(profile);
                        }

                    }
                });
    }

    public static String getUID() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser().getUid();
    }

    // calculate users new avg weight from previously era and use for new macro calculations
    public static double recalculate(WeightLogs weightLogs) {
        List<Log> logs = weightLogs.getLogs();

        currentWeight = WeightLogs.calculateAvgWeightFromLastEra(logs);
        return currentWeight;
    }

    // Harris-Benedict Equation, which takes into account age, height, and weight
    public static int calculateBMR() {
        boolean isMale = gender.equals("Male");

        // bases depending on gender
        double genderBase = isMale ? 66.0 : 655.0;
        double weightBase = isMale ? 13.7 : 9.6;
        double heightBase = isMale ? 5.0 : 1.8;
        double ageBase = isMale ? 6.8 : 4.7;

        double bmr = genderBase + (weightBase * currentWeight) + (heightBase * height) - (ageBase * getAge());
        return (int) bmr;
    }

    public static int calculateTDEE() {
        double tdee = activityLvl * calculateBMR();
        return (int) tdee;
    }

    public static int calculateCalsToConsume() {
        int tdee = calculateTDEE();
        int deficit = tdee / 5; // 20%
        return tdee - deficit;
    }

    public static Macros calculateMacros() {
        int CALS_PER_PROT_CARB = 4;
        int CALS_PER_FAT = 9;

        int calories = calculateCalsToConsume();

        // 40 - 40 - 20
        int fatCals = (int) (calories * 0.2);
        int proteinCals = (int) (calories * 0.4);
        int carbohydrateCals = (int) (calories * 0.4);

        int fat = fatCals / CALS_PER_FAT;
        int protein = proteinCals / CALS_PER_PROT_CARB;
        int carbohydrate = carbohydrateCals / CALS_PER_PROT_CARB;

        return new Macros(fat, protein, carbohydrate, calories);
    }

    public static int calculateWeeksToReachGoal() {
        double safeLossPerWeek = 0.45359237; // 1 pound / 0.45kg per week
        double currWeight = currentWeight;

        int weeks = 0;
        while(currWeight >= goalWeight) {
            currWeight -= safeLossPerWeek;
            weeks++;
        }

        return weeks;
    }

    public static void setLoadedFields(Profile profile) {
        gender = profile.getGender();
        birthday = profile.getBirthday();
        height = profile.getHeight();
        hasGoal = profile.isHasGoal();
    }

    public static void setLoadedGoal(Goal goal) {
        User.goal = goal;

        currentWeight = goal.getCurrentWeight();
        goalWeight = goal.getGoalWeight();
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        User.height = height;
    }

    public static void setCurrentWeight(int currentWeight) {
        User.currentWeight = currentWeight;
    }

    public static void setBirthday(Date birthday) {
        User.birthday = birthday;
    }

    public static void setGender(String gender) {
        User.gender = gender;
    }

    public static void setGoalWeight(int goalWeight) {
        User.goalWeight = goalWeight;
    }

    public static void setActivityLvl(double activityLvl) {
        User.activityLvl = activityLvl;
    }

    public static void setIntensityLvl(int intensityLvl) { User.intensityLvl = intensityLvl; }

    public static Date getBirthday() {
        return birthday;
    }

    public static void setWorkoutsPerWeek(int workoutsPerWeek) {
        User.workoutsPerWeek = workoutsPerWeek;
    }

    public static void setMinutesPerWorkout(int minutesPerWorkout) {
        User.minutesPerWorkout = minutesPerWorkout;
    }

    public static String getBirthdayFormated() {
        return D_FORMAT.format(birthday.getTime());
    }

    public static double getCurrentWeight() {
        return currentWeight;
    }

    public static double getGoalWeight() {
        return goalWeight;
    }

    public static Macros getMacros() {
        return goal.getMacros();
    }

    public static void setLoadedWeightLogs(WeightLogs weightLogs) {
        User.weightLogs = weightLogs;
    }

    public static WeightLogs getWeightLogs() {
        return weightLogs;
    }

    public static void setWeightLogs(WeightLogs weightLogs) {
        User.weightLogs = weightLogs;
    }
}
