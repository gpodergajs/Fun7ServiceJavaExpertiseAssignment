package com.gpode.enums;

public enum Status {

    DISABLED("disabled"),
    ENABLED("enabled");

    public final String label;

    Status(String label){
        this.label = label;
    }
}
