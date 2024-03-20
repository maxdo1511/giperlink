package ru.espada.giperlink.customer_module.customer.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.espada.giperlink.customer_module.customer.CustomerResponseEntity;
import ru.espada.giperlink.localization.LocalizationService;
import ru.espada.giperlink.localization.exception.LocalizationException;

/**
 * @author espada
 * @since 19.03.2024
 *
 * Обработчик исключений, связанных с клиентами
 * Используется для локализации исключений и
 */
@ControllerAdvice
public class CustomerExceptionAdvice {

    private LocalizationService localizationService;

    @Autowired
    public CustomerExceptionAdvice(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<?> handleCustomerException(CustomerException e) {
        try {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CustomerResponseEntity.builder()
                            .success(false)
                            .message(localizationService.getLocalizedMessage(e.getMessage(), e.getLanguage()))
                            .language(e.getLanguage())
                            .build());
        } catch (LocalizationException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CustomerResponseEntity.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .language(e.getLanguage())
                            .build());
        }
    }

}
