package ru.espada.giperlink.appeal.appeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.espada.giperlink.user.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppealRepository extends JpaRepository<AppealEntity, Long> {

    Optional<List<AppealEntity>> findAppealEntitiesByUserAndDateBetweenOrderByDate(UserEntity user, long date, long date2);

}
