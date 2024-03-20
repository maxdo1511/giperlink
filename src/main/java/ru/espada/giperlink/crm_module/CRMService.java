package ru.espada.giperlink.crm_module;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.espada.giperlink.crm_module.application.ApplicationEntity;
import ru.espada.giperlink.crm_module.application.ApplicationService;
import ru.espada.giperlink.crm_module.crm_models.request.ServiceModifyRequest;
import ru.espada.giperlink.crm_module.crm_models.request.VerifyCustomerRequest;
import ru.espada.giperlink.crm_module.crm_models.response.ApplicationResponse;
import ru.espada.giperlink.customer_module.customer.CustomerEntity;
import ru.espada.giperlink.customer_module.customer.CustomerService;

@Service
public class CRMService {


    private final CustomerService customerService;
    private final CRMService CRMService;
    private final ApplicationService applicationService;

    public CRMService(CustomerService customerService,
                      CRMService CRMService,
                      ApplicationService applicationService) {
        this.customerService = customerService;
        this.CRMService = CRMService;
        this.applicationService = applicationService;
    }

    public ResponseEntity<ApplicationResponse> verifyCustomer(VerifyCustomerRequest request) {
        CustomerEntity customer = customerService.getCustomerByNumber(request.getNumber());

        if (customer == null) {
            return ResponseEntity.badRequest().body(ApplicationResponse.builder().verified(false).build());
        }

        ApplicationResponse responseBody = applicationService.createNewApplication(customer);
        return ResponseEntity.ok().body(responseBody);
    }
}
