package ru.espada.giperlink.customer.customer_private;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(schema = "customer", name = "customer_private")
public class CustomerPrivateEntity {

    @Id
    private Long id;
    private String name;
    private String surname;
    private String patronymic;

}
