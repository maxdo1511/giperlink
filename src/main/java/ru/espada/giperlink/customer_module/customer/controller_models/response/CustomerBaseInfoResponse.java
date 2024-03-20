package ru.espada.giperlink.customer_module.customer.controller_models.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerBaseInfoResponse extends AbstractCustomerInfoResponse {

    private long id;
    private String name;
    private String surname;
    private String patronymic;

}
