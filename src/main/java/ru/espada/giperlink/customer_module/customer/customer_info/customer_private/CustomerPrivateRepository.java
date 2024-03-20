package ru.espada.giperlink.customer_module.customer.customer_info.customer_private;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPrivateRepository extends JpaRepository<CustomerPrivateEntity, Long> {
}
