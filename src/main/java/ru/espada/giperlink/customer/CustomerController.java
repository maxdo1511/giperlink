package ru.espada.giperlink.customer;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.espada.giperlink.customer.controller_models.request.CustomerBaseInfoRequest;
import ru.espada.giperlink.customer.controller_models.request.RemovePageSessionRequest;

/**
 * @author espada
 * @since 19.03.2024
 *
 * Контроллер взаимодействия с клиентами
 *
 * Каждый запрос от клиента содержит язык локализации, для локализации исключений
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
    * Получает базовую информацию о клиенте по запросу.
    *
    * @param  request   объект CustomerBaseInfoRequest, содержащий необходимую информацию
    * @return          ResponseEntity с базовой информацией о клиенте
    */
    @GetMapping("/get-base-info")
    public ResponseEntity<?> getCustomerBaseInfo(@Valid @RequestBody CustomerBaseInfoRequest request) {
        return customerService.getBaseInfo(request);
    }

    /**
    * Получает базовую информацию по пользователям.
    *
    * @param  request   объект CustomerBaseInfoRequest, содержащий необходимую информацию
    * @return          ResponseEntity страницу из клиентов определённого размера (значение по умолчанию в конфигурации customer.pageSize)
    */
    @GetMapping("/get-base-infos")
    public ResponseEntity<?> getBaseInfos(@Valid @RequestBody CustomerBaseInfoRequest request) {
        return customerService.getBaseInfos(request);
    }

    /**
    * Удаляет сеанс страницы.
    *
    * @param  request   объект RemovePageSessionRequest, содержащий необходимую информацию
    * @return          ResponseEntity с статусом 200 OK в случае успеха
    */
    @DeleteMapping("/remove-page-session")
    public ResponseEntity<?> removePageSession(@Valid @RequestBody RemovePageSessionRequest request) {
        customerService.removePageSession(request);
        return ResponseEntity.ok().build();
    }
}
