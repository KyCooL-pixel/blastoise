package com.blastoisefx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rgx {
    public static boolean regexValidateEmail(String input) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
