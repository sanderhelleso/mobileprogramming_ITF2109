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
        Query profilesQuery = profilesRef.whereEqualTo("uid", uid).limit(1);

        CollectionReference goalsRef = db.collection("goals");
        final Query goalsQuery = goalsRef.whereEqualTo("uid", uid).limit(1);

        profilesQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> profileTask) {
                        if (profileTask.isSuccessful()) {
                            boolean isEmpty = profileTask.getResult().isEmpty();
                            boolean mustFetchGoal = true;

                            // if no profile, goto Profile Setup
                            if (isEmpty) {
                                intent.setClass(activity, ProfileSetupActivity.class);
                            } else {
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) profileTask.getResult().getDocuments().get(0);
                                Profile profile = document.toObject(Profile.class);
                                User.setLoadedFields(profile);

                                // user has profile, but no goal setup, goto Goal setup
                                if (!profile.hasGoal) {
                                    intent.setClass(activity, GoalStatsActivity.class);
                                    mustFetchGoal = false;
                                } else {
                                    goalsQuery.get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> goalsTask) {
                                                    if (goalsTask.isSuccessful()) {
                                                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) goalsTask.getResult().getDocuments().get(0);
                                                        Goal goal = document.toObject(Goal.class);
                                                        User.setLoadedGoal(goal);

                                                        // user has goal, goto Home
                                                        intent.setClass(activity, HomeActivity.class);
                                                        replaceActivity(intent, activity);
                                                    }
                                                }
                                            });
                                }

                                if (!mustFetchGoal) {
                                    replaceActivity(intent, activity);
                                }
                            }

                        } else {
                            auth.signOut();
                            intent.setClass(activity, AuthActivity.class);
                            replaceActivity(intent, activity);
                        }
                    }
                });
    }

    public static void replaceActivity(final Intent intent, Activity activity) {
        activity.startActivity(intent);
        activity.finishAffinity();
    }
}
