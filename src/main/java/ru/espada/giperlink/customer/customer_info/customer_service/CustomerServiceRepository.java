package ru.espada.giperlink.customer.customer_info.customer_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerServiceEntity, Long> {
}
