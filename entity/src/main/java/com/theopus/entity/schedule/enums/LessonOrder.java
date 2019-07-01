package com.theopus.entity.schedule.enums;

/**
 * Created by Oleksandr_Tkachov on 9/15/2017.
 */
public enum LessonOrder {
    NONE(-1),
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8),
    ;

    private final int order;

    LessonOrder(int i) {
        order = i;
    }
}
