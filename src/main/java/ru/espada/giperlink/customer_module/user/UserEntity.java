package ru.espada.giperlink.customer_module.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.customer_module.user.user_private.UserPrivateEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "g_user", name = "g_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @OneToOne
    @JoinColumn(name = "user_private_id")
    private UserPrivateEntity userPrivate;

}
