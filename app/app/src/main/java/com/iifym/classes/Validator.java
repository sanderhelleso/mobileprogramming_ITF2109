package com.iifym.classes;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Validator {
    private final static Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
    private final static int MIN_PW_LEN = 8;
    private final static int MAX_PW_LEN = 40;

    private static boolean validateEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    private static boolean validatePassword(String pass) {
        int len = pass.length();
        if (len < MIN_PW_LEN || len > MAX_PW_LEN) {
            return false;
        }

        for (char c : pass.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }

    public static boolean validateEmailAndPass(String email, String pass, Context ctx) {
        if (!validateEmail(email)) {
            Toast.makeText(ctx, "The e-mail provided is invalid", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!validatePassword(pass)) {
            Toast.makeText(ctx, "Password must be between 8 - 40 characters and contain at least 1 digit", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public static boolean comparePass(String pass1, String pass2, Context ctx) {
        if (!pass1.equals(pass2)) {
            Toast.makeText(ctx, "Passwords does not match", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
