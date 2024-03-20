package ru.espada.giperlink.customer_module.customer.controller_models.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.espada.giperlink.forum_module.appeal.appeal.AppealEntity;
import ru.espada.giperlink.customer_module.customer.CustomerEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerFullInfoResponse extends AbstractCustomerInfoResponse {

    private CustomerEntity customer;
    private List<AppealEntity> appealList;

}
