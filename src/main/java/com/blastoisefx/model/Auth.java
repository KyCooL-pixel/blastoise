package com.blastoisefx.model;

import com.blastoisefx.App;
import com.blastoisefx.utils.Rgx;

public class Auth {
    public static User validate(String email, String password) {
        for (User user : App.getUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public enum AuthStatus {
        SUCCESS,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        INVALID_EMAIL_AND_PASSWORD
    }

    public static AuthStatus addNewUser(String email, String password) {
        boolean isEmailValid = Rgx.regexValidateEmail(email);
        boolean isPasswordValid = password.length() >= 8;

        if (isEmailValid && isPasswordValid) {
            App.getUsers().add(new User(email, password));
            return AuthStatus.SUCCESS;
        }

        if (!isEmailValid && isPasswordValid) {
            return AuthStatus.INVALID_EMAIL;
        }

        if (!isPasswordValid && isEmailValid) {
            return AuthStatus.INVALID_PASSWORD;
        }

        return AuthStatus.INVALID_EMAIL_AND_PASSWORD;
    }

}
