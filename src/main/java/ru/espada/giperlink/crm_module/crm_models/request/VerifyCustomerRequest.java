package ru.espada.giperlink.crm_module.crm_models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyCustomerRequest {

    private String number;
    private String surname;

}
