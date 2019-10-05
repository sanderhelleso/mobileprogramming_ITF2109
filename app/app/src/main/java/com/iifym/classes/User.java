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
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference profilesRef = db.collection("profiles");
        Profile profile = new Profile(uid, gender, birthday, height, false);
        profilesRef.add(profile);
    }

    public static void saveGoal() {

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


    public static Date getBirthday() {
        return birthday;
    }

    public static String getBirthdayFormated() {
        return D_FORMAT.format(birthday.getTime());
    }
}
