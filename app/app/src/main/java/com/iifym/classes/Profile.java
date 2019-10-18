package com.iifym.classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Profile {

    private String uid;
    private String gender;
    private Date birthday;
    private int height;
    private boolean hasGoal;

    public Profile() {}

    public Profile(String uid, String gender, Date birthday, int height, boolean hasGoal) {
        this.uid = uid;
        this.gender = gender;
        this.birthday = birthday;
        this.height = height;
        this.hasGoal = hasGoal;
    }

    public String getUid() {
        return uid;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHasGoal() {
        return hasGoal;
    }

    public void setHasGoal() {
        this.hasGoal = true;
    }
}
