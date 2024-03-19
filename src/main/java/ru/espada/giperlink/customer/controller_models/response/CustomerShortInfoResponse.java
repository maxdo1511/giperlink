package ru.espada.giperlink.customer.controller_models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.customer.contract.ContractEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerShortInfoResponse {

    private Long id;
    private String number;
    private String name;
    private String surname;
    private String patronymic;
    private List<ContractEntity> contract;
    private float personalAccount;
    private String connectedServices;

}
