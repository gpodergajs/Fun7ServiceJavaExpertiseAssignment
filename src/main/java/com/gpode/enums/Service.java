package com.gpode.enums;

public enum Service {

    ADS("ads"),
    MULTIPLAYER("multiplayer"),
    USER_SUPPORT("user_support");

    public final String label;

    Service(String label){
        this.label = label;
    }
}
