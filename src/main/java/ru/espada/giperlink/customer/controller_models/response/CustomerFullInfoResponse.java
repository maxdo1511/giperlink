package ru.espada.giperlink.customer.controller_models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.appeal.appeal.AppealEntity;
import ru.espada.giperlink.customer.CustomerEntity;
import ru.espada.giperlink.customer.contract.ContractEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerFullInfoResponse {

    private CustomerEntity customer;
    private List<AppealEntity> appealList;

}
