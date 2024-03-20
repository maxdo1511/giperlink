package ru.espada.giperlink.customer.controller_models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerFullInfoByFieldInfoRequest {

    private String field;
    private String value;
    private String lang;

}
