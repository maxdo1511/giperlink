package ru.espada.giperlink.crm_module.crm_models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceModifyRequest {

    private String uuid;
    private String service;
    private String serviceKind;
    private String serviceType;
    private String problemType;

}
