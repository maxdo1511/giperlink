package ru.espada.giperlink.crm_module.crm_models.response;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.crm_module.application.ApplicationEntity;
import ru.espada.giperlink.hardware_module.hardware.HardwareEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationResponse {

    private boolean verified;

    private String uuid;
    private long creationDate;
    private String customerNumber;
    private float personalAccount;
    private String service;
    private String serviceKind;
    private String serviceType;
    private String status;
    private HardwareEntity hardware;
    private String problemType;
    private String description;

}
