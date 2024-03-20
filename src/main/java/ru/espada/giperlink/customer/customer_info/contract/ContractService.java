package ru.espada.giperlink.customer.customer_info.contract;

import org.springframework.stereotype.Service;

@Service
public class ContractService {


    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
}
