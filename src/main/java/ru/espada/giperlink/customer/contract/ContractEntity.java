package ru.espada.giperlink.customer.contract;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.customer.CustomerEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "customer", name = "contract")
public class ContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contractNumber;
    private Long dateOfIssue;
    private Long type;
    private Long dateOfTermination;
    private String terminationReason;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

}
