package ru.espada.giperlink.customer.controller_models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerBaseInfoResponse {

    private long id;
    private String name;
    private String surname;
    private String patronymic;

}
