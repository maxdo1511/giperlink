package ru.espada.giperlink.customer.connected_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectedServiceRepository extends JpaRepository<ConnectedServiceEntity, Long> {
    Optional<List<ConnectedServiceEntity>> findAllByCustomerId(Long customerId);
}
