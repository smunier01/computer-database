package com.excilys.core.conflict.format;

public enum ErrorMessage {
    DATE_FORMAT("error.date.format"),
    DATE_BEFORE_TIMESTAMP("error.date.before.timestamp"),
    DATE_AFTER_TIMESTAMP("error.date.after.timestamp"),
    INTRODUCED_AFTER_DISCONTINUED("error.introduced.after.discontinued"),
    NAME_NULL("error.name.null"),
    COMPANY_NOT_FOUND("error.company.notfound");

    String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
