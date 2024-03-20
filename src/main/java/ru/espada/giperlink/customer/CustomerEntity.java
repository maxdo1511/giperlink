package ru.espada.giperlink.customer;

import jakarta.persistence.*;
import lombok.*;
import ru.espada.giperlink.customer.annotation.CustomerSearchFiled;
import ru.espada.giperlink.customer.customer_info.contract.ContractEntity;
import ru.espada.giperlink.customer.customer_info.customer_hardware.CustomerHardwareEntity;
import ru.espada.giperlink.customer.customer_info.customer_private.CustomerPrivateEntity;
import ru.espada.giperlink.user.UserEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "customerEntityBuilder")
@Entity
@Table(schema = "customer", name = "customer")
public class CustomerEntity extends UserEntity {

    @Id
    private Long id;
    private String number;
    @OneToMany(mappedBy = "customer")
    @Column(name = "contract_id")
    private List<ContractEntity> contracts;
    @CustomerSearchFiled(many = true)
    private Long personalAccount;
    @OneToOne
    @JoinColumn(name = "customer_private_id")
    private CustomerPrivateEntity customerPrivate;
    @OneToMany(mappedBy = "customer")
    @Column(name = "customer_hardware_id")
    private List<CustomerHardwareEntity> customerHardware;

}
