package ru.espada.giperlink.customer.util_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.espada.giperlink.customer_module.customer.util_service.CustomerFindService;

import java.util.List;

@SpringBootTest
public class TestCustomerFindService {


    private CustomerFindService customerFindService;

    @Test
    public void testFindAllCustomersByField() {
        List<?> list = customerFindService.findAllCustomersByField("personalAccount", "100");

        System.out.println(list);
    }

    @Autowired
    public void setCustomerFindService(CustomerFindService customerFindService) {
        this.customerFindService = customerFindService;
    }
}
