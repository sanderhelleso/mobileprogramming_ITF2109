package com.iifym.classes;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.iifym.AuthActivity;
import com.iifym.HomeActivity;
import com.iifym.GoalStatsActivity;
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

                            // if no profile, goto Profile Setup
                            if (isEmpty) {
                                intent.setClass(activity, ProfileSetupActivity.class);
                            } else {
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                                Profile profile = document.toObject(Profile.class);
                                User.setLoadedFields(profile);

                                // user has profile, but no goal setup, goto Goal setup
                                if (!profile.hasGoal) {
                                    intent.setClass(activity, GoalStatsActivity.class);
                                } else {

                                    // user has goal, goto Home
                                    intent.setClass(activity, HomeActivity.class);
                                }
                            }

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
