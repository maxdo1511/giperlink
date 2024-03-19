package ru.espada.giperlink.user;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.espada.giperlink.localization.LocalizationService;

/**
 * @author espada
 * @since 19.03.2024
 *
 * Исключение для клиентов
 */
@Getter
public class UserException extends RuntimeException {

    private String message;
    private String language;

    public UserException(String message, String language) {
        this.message = message;
        this.language = language;
    }

    public UserException(String message) {
        this(message, LocalizationService.DEFAULT_LOCALE);
    }

}
