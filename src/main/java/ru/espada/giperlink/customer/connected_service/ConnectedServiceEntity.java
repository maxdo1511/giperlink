package ru.espada.giperlink.customer.connected_service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.giperlink.customer.CustomerEntity;
import ru.espada.giperlink.customer.customer_service.CustomerServiceEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "customer", name = "connected_service")
public class ConnectedServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private CustomerEntity customer;
    @OneToOne
    @JoinColumn(name = "customer_service_id")
    private CustomerServiceEntity services;

}