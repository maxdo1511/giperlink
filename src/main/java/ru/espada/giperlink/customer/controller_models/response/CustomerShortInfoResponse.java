package ru.espada.giperlink.customer.controller_models.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.espada.giperlink.customer.customer_info.contract.ContractEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerShortInfoResponse extends AbstractCustomerInfoResponse {

    private Long id;
    private String number;
    private String name;
    private String surname;
    private String patronymic;
    private List<ContractEntity> contract;
    private float personalAccount;
    private String connectedServices;

}
