package com.iifym.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.iifym.AuthActivity;
import com.iifym.HomeActivity;
import com.iifym.ProfileSetupActivity;

public class IntentSelector {

    public static void hasSetupProfile(FirebaseUser user, final FirebaseAuth auth, final Intent intent, final Activity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = user.getUid();

        CollectionReference profilesRef = db.collection("profiles");
        Query query = profilesRef.whereEqualTo("uid", uid).limit(1);

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean isEmpty = task.getResult().isEmpty();
                            intent.setClass(activity, isEmpty ? ProfileSetupActivity.class : HomeActivity.class);
                        } else {
                            auth.signOut();
                            intent.setClass(activity, AuthActivity.class);
                        }

                        replaceActivity(intent, activity);
                    }
                });
    }

    public static void replaceActivity(final Intent intent, Activity activity) {
        activity.startActivity(intent);
        activity.finishAffinity();
    }
}
