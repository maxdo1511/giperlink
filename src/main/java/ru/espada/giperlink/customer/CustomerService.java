package ru.espada.giperlink.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.espada.giperlink.customer.controller_models.request.CustomerBaseInfoRequest;
import ru.espada.giperlink.customer.controller_models.request.RemovePageSessionRequest;
import ru.espada.giperlink.customer.controller_models.response.CustomerBaseInfoResponse;
import ru.espada.giperlink.user.UserEntity;
import ru.espada.giperlink.user.UserException;
import ru.espada.giperlink.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author espada
 * @since 19.03.2024
 *
 * Сервис для работы с клиентами, выполняет запросы из API.
 * Для работы необходим конфигурационный файл и сервис локализации для обработки искючений.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final CustomerServiceConfig customerServiceConfig;
    /**
     * Поле содержит активные сессии зпросов пользователей из бд
     * Ключ - id пользователя
     * Значение - текущая страница
     */
    private Map<Long, Integer> activePageSessions;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserService userService, CustomerServiceConfig customerServiceConfig) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.customerServiceConfig = customerServiceConfig;

        activePageSessions = new HashMap<>();
    }

    /**
     * @param request запрос из API
     * @return базовая информация о клиенте (id, ФИО)
     * @throws ru.espada.giperlink.user.UserException исключение при отсутствии пользователя
     */
    public ResponseEntity<CustomerBaseInfoResponse> getBaseInfo(CustomerBaseInfoRequest request) throws UserException {
        UserEntity user = userService.getUserById(request.getUserId(), request.getLang());

        return ResponseEntity
                .status(200)
                .body(CustomerBaseInfoResponse.builder()
                        .id(user.getId())
                        .name(user.getUserPrivate().getName())
                        .surname(user.getUserPrivate().getSurname())
                        .patronymic(user.getUserPrivate().getPatronymic())
                        .build());
    }

    public ResponseEntity<List<CustomerBaseInfoResponse>> getBaseInfos(CustomerBaseInfoRequest request) throws UserException {
        int page = updatePageSession(request.getUserId());
        List<UserEntity> users = userService.getUsers(request.getLang(), page, customerServiceConfig.getPageSize());
        return ResponseEntity
                .status(200)
                .body(users.stream().map(user -> CustomerBaseInfoResponse.builder()
                        .id(user.getId())
                        .name(user.getUserPrivate().getName())
                        .surname(user.getUserPrivate().getSurname())
                        .patronymic(user.getUserPrivate().getPatronymic())
                        .build())
                        .toList());
    }

    public void removePageSession(RemovePageSessionRequest request) {
        activePageSessions.remove(request.getUserId());
    }

    /**
     * Обновляет счетчик активных сессий
     * @return текущая страница
     */
    private int updatePageSession(long userId) {
        if (activePageSessions.containsKey(userId)) {
            activePageSessions.put(userId, activePageSessions.get(userId) + 1);
        } else {
            activePageSessions.put(userId, 0);
        }
        return activePageSessions.get(userId);
    }
}
