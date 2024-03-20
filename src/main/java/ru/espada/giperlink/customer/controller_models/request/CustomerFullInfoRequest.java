package ru.espada.giperlink.customer.controller_models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerFullInfoRequest {

    private Long id;
    private String lang;

}
