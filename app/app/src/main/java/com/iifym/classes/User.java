package com.iifym.classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User {
    private static String gender;
    private static Date birthday;
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
}
