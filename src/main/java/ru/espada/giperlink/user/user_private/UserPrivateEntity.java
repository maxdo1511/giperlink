package ru.espada.giperlink.user.user_private;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "g_user", name = "user_private")
public class UserPrivateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String patronymic;
    private int passportSeries;
    private int passportNumber;
    private long passportDateOfIssue;
    private String passportIssuedBy;

}
