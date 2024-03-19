package ru.espada.giperlink.customer.connected_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectedServiceRepository extends JpaRepository<ConnectedServiceEntity, Long> {

}
