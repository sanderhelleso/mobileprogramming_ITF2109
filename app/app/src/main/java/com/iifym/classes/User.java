package com.iifym.classes;

import java.util.Date;

public class User {
    private static String gender;
    private static Date birthday;

    public static void setGender(String _gender) {
        gender = _gender;
    }

    public static String getGender() {
        return gender;
    }
}
