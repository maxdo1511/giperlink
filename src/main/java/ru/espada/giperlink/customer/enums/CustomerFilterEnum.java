package ru.espada.giperlink.customer.enums;

import lombok.Getter;

@Getter
public enum CustomerFilterEnum {

    ACTIVE(0),
    INACTIVE(0),
    ALL(0);

    private final int priority;

    CustomerFilterEnum(int priority) {
        this.priority = priority;
    }
}
