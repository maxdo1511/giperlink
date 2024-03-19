package ru.espada.giperlink.appeal.message;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.appeal.appeal.AppealEntity;
import ru.espada.giperlink.user.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "appeal", name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appeal_id")
    private AppealEntity appeal;
    @OneToOne
    @JoinColumn(name = "sender_id")
    private UserEntity user;
    private String text;
    private long date;

}
