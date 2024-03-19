package ru.espada.giperlink.customer.customer_hardware;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.customer.CustomerEntity;
import ru.espada.giperlink.customer.contract.ContractEntity;

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

    private long hardwareId;
    @OneToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

}
