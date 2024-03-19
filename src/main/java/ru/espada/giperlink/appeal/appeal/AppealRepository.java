package ru.espada.giperlink.appeal.appeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppealRepository extends JpaRepository<AppealEntity, Long> {
}
