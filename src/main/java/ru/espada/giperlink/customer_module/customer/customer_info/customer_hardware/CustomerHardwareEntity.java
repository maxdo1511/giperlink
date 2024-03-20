package ru.espada.giperlink.customer_module.customer.customer_info.customer_hardware;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.customer_module.customer.CustomerEntity;
import ru.espada.giperlink.customer_module.customer.customer_info.contract.ContractEntity;
import ru.espada.giperlink.hardware_module.hardware.HardwareEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "customer", name = "customer_hardware")
public class CustomerHardwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "hardware_id")
    private HardwareEntity hardware;
    @OneToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

}
