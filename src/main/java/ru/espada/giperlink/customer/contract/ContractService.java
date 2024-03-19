package ru.espada.giperlink.customer.contract;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {


    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
}
