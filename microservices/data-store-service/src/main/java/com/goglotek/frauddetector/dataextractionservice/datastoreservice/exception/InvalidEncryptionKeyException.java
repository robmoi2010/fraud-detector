package com.goglotek.frauddetector.dataextractionservice.datastoreservice.exception;

public class InvalidEncryptionKeyException extends GoglotekException {
    public InvalidEncryptionKeyException() {
        super();
    }

    public InvalidEncryptionKeyException(GoglotekException e) {
        super(e);
    }

    public InvalidEncryptionKeyException(GoglotekException e, String msg) {
        super(e, msg);
    }

    public InvalidEncryptionKeyException(String msg) {
        super(msg);
    }
}
