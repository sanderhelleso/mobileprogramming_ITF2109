package com.iifym.classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Profile {

    public String uid;
    public String gender;
    public Date birthday;
    public int height;

    public Profile() {}

    public Profile(String uid, String gender, Date birthday, int height) {
        this.uid = uid;
        this.gender = gender;
        this.birthday = birthday;
        this.height = height;
    }
}
