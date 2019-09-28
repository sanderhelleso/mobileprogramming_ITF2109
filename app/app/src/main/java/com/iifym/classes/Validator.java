package com.iifym.classes;

import java.util.regex.Pattern;

public class Validator {
    private final static Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
    private final static int MIN_PW_LEN = 8;
    private final static int MAX_PW_LEN = 40;

    public static boolean validateEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean validatePassword(String pass) {
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
}
