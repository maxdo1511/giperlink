package ru.espada.giperlink.customer_module.customer.customer_info.connected_service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectedServiceService {


    private final ConnectedServiceRepository connectedServiceRepository;

    public ConnectedServiceService(ConnectedServiceRepository connectedServiceRepository) {
        this.connectedServiceRepository = connectedServiceRepository;
    }

    public List<ConnectedServiceEntity> getConnectedServices(long customerId) {
        return connectedServiceRepository.findAllByCustomerId(customerId).orElse(new ArrayList<>());
    }
}
