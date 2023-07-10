package com.tech.log.enumType;

import java.util.Arrays;

public enum Status {
    ERROR("10"),
    SUCCESS("200"),
    NOT_FOUND("404");

    private String code;


    Status(String code) {
        this.code = code;
    }


    public static Status getStatus(String code) {
        return Arrays.stream(Status.values())
                .filter(status -> status.equals(code))
                .findAny()
                .orElse(null);
    }
//
//    public boolean hasStatus(String value) {
//        return statusList.stream().anyMatch(status -> status.equals(value));
//    }
}
