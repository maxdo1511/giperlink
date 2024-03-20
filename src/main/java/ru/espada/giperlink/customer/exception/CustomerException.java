package ru.espada.giperlink.customer.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.espada.giperlink.localization.LocalizationService;

/**
 * @author espada
 * @since 19.03.2024
 *
 * Ошибка при работе с клиентами при выполнении запросов из API,
 * предполагает использование сервиса локализации при обработке искючений
 *
 * В качестве языка используется русский по умолчанию
 */
@Getter
public class CustomerException extends RuntimeException {

    private String language = LocalizationService.DEFAULT_LOCALE;

    public CustomerException(String message, String language) {
        super(message);
        this.language = language;
    }

    public CustomerException(String message) {
        super(message);
    }
}
