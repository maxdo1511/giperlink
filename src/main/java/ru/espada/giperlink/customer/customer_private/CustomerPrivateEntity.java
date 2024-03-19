package ru.espada.giperlink.customer.customer_private;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.customer.CustomerEntity;
import ru.espada.giperlink.customer.adress.AddressEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "customer", name = "customer_private")
public class CustomerPrivateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "address")
    private AddressEntity address;

}
