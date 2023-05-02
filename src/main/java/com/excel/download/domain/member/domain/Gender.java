package com.excel.download.domain.member.domain;

public enum Gender {

    M("남성"),
    F("여성");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
