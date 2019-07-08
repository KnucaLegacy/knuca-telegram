package com.theopus.entity.schedule.enums;

public enum YearOfStudy {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    ;

    private final int intVal;

    YearOfStudy(int intVal) {
        this.intVal = intVal;
    }

    public int intVal() {
        return intVal;
    }
}
