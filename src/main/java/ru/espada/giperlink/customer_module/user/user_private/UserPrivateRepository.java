package ru.espada.giperlink.customer_module.user.user_private;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrivateRepository extends JpaRepository<UserPrivateEntity, Long> {
}
