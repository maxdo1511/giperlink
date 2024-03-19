package ru.espada.giperlink.appeal.appeal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.appeal.theme.ThemeEntity;
import ru.espada.giperlink.user.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "appeal", name = "appeal")
public class AppealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "theme_id")
    private ThemeEntity theme;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private long date;

}
