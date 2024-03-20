package ru.espada.giperlink.crm_module.application.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {

    NEW(0, "Новая заявка"),
    IN_PROGRESS(1, "Требует выезда"),
    CLOSED(2, "Закрыта");

    private final int status;
    private final String name;

    StatusEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public static StatusEnum getByName(String name) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.name.equals(name)) {
                return status;
            }
        }
        return null;
    }

    public static StatusEnum getByStatus(int status) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.status == status) {
                return statusEnum;
            }
        }
        return null;
    }

}
