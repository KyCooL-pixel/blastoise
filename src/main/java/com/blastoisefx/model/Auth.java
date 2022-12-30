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

    public static int addNewUser(String email, String password) {
        if (Rgx.regexValidateEmail(email) && password.length() >= 8) {
            App.getUsers().add(new User(email, password));
            return 0;
        }
        else if(!Rgx.regexValidateEmail(email) && password.length() >= 8){
            return 1;
        }
        else if(Rgx.regexValidateEmail(email) && !(password.length() >= 8)){
            return 2;
        }
        else{
            return 3;
        }
    }

}
