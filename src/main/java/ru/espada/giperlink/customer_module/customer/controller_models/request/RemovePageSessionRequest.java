package ru.espada.giperlink.customer_module.customer.controller_models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemovePageSessionRequest {

    private long userId;
    private String lang;

}
