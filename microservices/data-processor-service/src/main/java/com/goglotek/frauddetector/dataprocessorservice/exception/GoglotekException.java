package com.goglotek.frauddetector.dataprocessorservice.exception;

public class GoglotekException extends Exception {
    public GoglotekException() {
        super();
    }

    public GoglotekException(Exception e) {
        super(e);
    }

    public GoglotekException(Exception e, String msg) {
        super(msg, e);
    }

    public GoglotekException(String msg) {
        super(msg);
    }
}
