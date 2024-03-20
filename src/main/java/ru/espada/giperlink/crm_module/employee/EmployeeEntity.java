package ru.espada.giperlink.crm_module.employee;

import jakarta.persistence.*;
import lombok.*;
import ru.espada.giperlink.customer_module.user.UserEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "employeeEntityBuilder")
@Entity
@Table(schema = "employee", name = "employee")
public class EmployeeEntity extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

}
