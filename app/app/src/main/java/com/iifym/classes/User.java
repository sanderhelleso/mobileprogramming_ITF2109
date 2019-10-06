package com.iifym.classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class User {
    private static String gender;
    private static Date birthday;
    private static int height;
    private static int currentWeight;
    private static int goalWeight;
    private static int activityLvl;
    private static int intensityLvl;

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

    public static void saveGoal() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference goalsRef = db.collection("goals");
        Goal goal = new Goal(getUID(), currentWeight, currentWeight, goalWeight, activityLvl, intensityLvl, 10);
        goalsRef.add(goal);
    }

    public static String getUID() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser().getUid();
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

    public static void setActivityLvl(int activityLvl) {
        User.activityLvl = activityLvl;
    }

    public static void setIntensityLvl(int intensityLvl) { User.intensityLvl = intensityLvl; }

    public static Date getBirthday() {
        return birthday;
    }

    public static String getBirthdayFormated() {
        return D_FORMAT.format(birthday.getTime());
    }
}
