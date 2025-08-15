package com.goglotek.fraud_detector.dataextractionservice.exception;

public class UnimplementedFeatureException extends GoglotekException {
    public UnimplementedFeatureException() {
        super();
    }

    public UnimplementedFeatureException(Exception e) {
        super(e);
    }

    public UnimplementedFeatureException(Exception e, String msg) {
        super(e, msg);
    }

    public UnimplementedFeatureException(String msg) {
        super(msg);
    }
}
