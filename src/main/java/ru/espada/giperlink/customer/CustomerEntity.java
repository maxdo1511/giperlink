package ru.espada.giperlink.customer;

import jakarta.persistence.*;
import lombok.*;
import ru.espada.giperlink.customer.contract.ContractEntity;
import ru.espada.giperlink.customer.customer_hardware.CustomerHardwareEntity;
import ru.espada.giperlink.customer.customer_private.CustomerPrivateEntity;
import ru.espada.giperlink.user.UserEntity;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "customerEntityBuilder")
@Entity
@Table(schema = "customer", name = "customer")
public class CustomerEntity extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @OneToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;
    private Long personalAccount;
    @OneToOne
    @JoinColumn(name = "customer_private_id")
    private CustomerPrivateEntity customerPrivate;
    @OneToOne
    @JoinColumn(name = "customer_hardware_id")
    private CustomerHardwareEntity customerHardware;

}
