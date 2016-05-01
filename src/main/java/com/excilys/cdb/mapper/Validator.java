package com.excilys.cdb.mapper;

import java.util.regex.Pattern;

public enum Validator {
    INSTANCE;

    private final Pattern intRegex = Pattern.compile("[0-9]*[1-9][0-9]*");

    private final Pattern dateRegex = Pattern.compile("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");

    private Validator() {

    }

    public static Validator getInstance() {
        return INSTANCE;
    }

    public boolean validateInt(String s) {
        return intRegex.matcher(s).matches();
    }

    public boolean validateDate(String s) {
        return dateRegex.matcher(s).matches();
    }
}
