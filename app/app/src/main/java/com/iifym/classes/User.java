package com.iifym.classes;

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

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        User.height = height;
    }

    public static int getCurrentWeight() {
        return currentWeight;
    }

    public static void setCurrentWeight(int currentWeight) {
        User.currentWeight = currentWeight;
    }

    public static int getGoalWeight() {
        return goalWeight;
    }

    public static void setGoalWeight(int goalWeight) {
        User.goalWeight = goalWeight;
    }

    public static int getActivityLvl() {
        return activityLvl;
    }

    public static void setActivityLvl(int activityLvl) {
        User.activityLvl = activityLvl;
    }

    private static final SimpleDateFormat D_FORMAT = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

    public static void setGender(String _gender) {
        gender = _gender;
    }

    public static String getGender() {
        return gender;
    }

    public static void setBirthday(Date date) {
        birthday = date;
    }

    public static Date getBirthday() {
        return birthday;
    }

    public static String getBirthdayFormated() {
        return D_FORMAT.format(birthday.getTime());
    }

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
}
