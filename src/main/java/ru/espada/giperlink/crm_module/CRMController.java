package ru.espada.giperlink.crm_module;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.espada.giperlink.crm_module.crm_models.request.VerifyCustomerRequest;

@RestController
@RequestMapping("/crm")
public class CRMController {


    private final CRMService CRMService;

    public CRMController(CRMService CRMService) {
        this.CRMService = CRMService;
    }

    @PostMapping("/verfy-customer")
    public ResponseEntity<?> verifyCustomer(@Valid @RequestBody VerifyCustomerRequest request) {
        return CRMService.verifyCustomer(request);
    }
}
