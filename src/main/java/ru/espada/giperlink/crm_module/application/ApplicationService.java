package ru.espada.giperlink.crm_module.application;

import org.springframework.stereotype.Service;
import ru.espada.giperlink.crm_module.application.enums.StatusEnum;
import ru.espada.giperlink.crm_module.crm_models.response.ApplicationResponse;
import ru.espada.giperlink.customer_module.customer.CustomerEntity;

import java.util.Date;
import java.util.UUID;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    public ApplicationResponse createNewApplication(CustomerEntity customer) {
        Date date = new Date();

        // uuid
        StringBuilder uuid = new StringBuilder();
        uuid.append(UUID.randomUUID().toString());
        uuid.append("-");
        uuid.append(customer.getId());
        uuid.append("/");
        uuid.append(date.getDay());
        uuid.append("/");
        uuid.append(date.getMonth());
        uuid.append("/");
        uuid.append(date.getYear());

        ApplicationResponse applicationResponse = ApplicationResponse.builder()
                .verified(true)
                .uuid(uuid.toString())
                .creationDate(date.getTime())
                .customerNumber(customer.getNumber())
                .personalAccount(customer.getPersonalAccount())
                .status(StatusEnum.NEW.getName())
                .build();
        return applicationResponse;
    }
}
