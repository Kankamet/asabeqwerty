package com.example.kankamet.asabeqwerty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kankamet on 4.11.2017.
 */

    // full name max=30 min =6
    //username min 6 max 10
    //min password 6 max 16



public class Validation {
    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPass(String pass) {
        String PASS_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,16}$";
        Pattern pattern = Pattern.compile(PASS_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
    public boolean isValidFullName(String fullName) {
        if (fullName != null && fullName.length() >= 6  && fullName.length()<=30) {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isAlpha(String name) {
        return name.matches("^\\s*[a-zA-Z,ç,Ç,ğ,Ğ,ı,İ,ö,Ö,ş,Ş,ü,Ü,\\s]+\\s*$");
    }
}
