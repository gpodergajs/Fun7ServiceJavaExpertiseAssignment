package com.gpode.enums;



public enum QueryParameter {

    TIMEZONE("timezone"),
    CC("cc"),
    USER_ID("userId");

    public final String label;
    QueryParameter(String label) {
        this.label = label;
    }
}
