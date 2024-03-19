package ru.espada.giperlink.customer.customer_hardware;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerHardwareRepository extends JpaRepository<CustomerHardwareEntity, Long> {
}
