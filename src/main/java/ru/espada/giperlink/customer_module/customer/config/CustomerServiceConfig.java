package ru.espada.giperlink.customer_module.customer.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "customer")
public class CustomerServiceConfig {

    private int pageSize;

}
