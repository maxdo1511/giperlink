package ru.espada.giperlink.forum_module.appeal.theme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity, Long> {
}
