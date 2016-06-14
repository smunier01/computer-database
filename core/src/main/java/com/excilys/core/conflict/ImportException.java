package com.excilys.core.conflict;

public class ImportException extends RuntimeException {

    public ImportException(String msg, Exception e) {
        super(msg, e);
    }

    public ImportException(String msg) {
        super(msg);
    }
}
