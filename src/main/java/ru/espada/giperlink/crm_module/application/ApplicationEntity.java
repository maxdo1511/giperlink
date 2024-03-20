package ru.espada.giperlink.crm_module.application;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.hardware_module.hardware.HardwareEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "crm", name = "application")
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private long creationDate;
    private String customerNumber;
    private float personalAccount;
    private int service;
    private int serviceKind;
    private int serviceType;
    private int status;
    @OneToOne
    @JoinColumn(name = "hardware_id")
    private HardwareEntity hardware;
    private int problemType;
    private String description;

}
