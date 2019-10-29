package com.iifym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iifym.classes.IntentSelector;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        IntentSelector.replaceActivity(new Intent(this, AuthActivity.class), this);
    }

    private void deleteAccount(String password, final View view) {
        if (password.equals("")) {
            Toast.makeText(getApplicationContext(), "No password provided", Toast.LENGTH_LONG).show();
            return;
        }

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credentials = EmailAuthProvider.getCredential(currentUser.getEmail(), password);
        currentUser.reauthenticate(credentials).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    currentUser.delete();
                    logout(view);
                    Toast.makeText(getApplicationContext(), "Your account has been deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid password provided", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteAccountPrompt(final View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(SettingsActivity.this);
        input.setHint("My password");
        input.setHeight(190);
        input.setPadding(30, 70, 30, 40);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());

        ColorStateList colorStateList = ColorStateList.valueOf(this.getResources().getColor(R.color.indigo));
        ViewCompat.setBackgroundTintList(input, colorStateList);

        builder.setView(input);
        builder.setTitle("Are you sure?");
        builder.setMessage("All your data will be deleted. This action is irreversible!")
                .setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteAccount(input.getText().toString(), view);
                    }
                })
                .setNegativeButton("No, Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.hint));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.mainRed));
            }
        });

        alertDialog.show();
    }
}
