package ru.espada.giperlink.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.espada.giperlink.appeal.AppealService;
import ru.espada.giperlink.customer.connected_service.ConnectedServiceEntity;
import ru.espada.giperlink.customer.connected_service.ConnectedServiceService;
import ru.espada.giperlink.customer.contract.ContractEntity;
import ru.espada.giperlink.customer.controller_models.request.CustomerBaseInfoRequest;
import ru.espada.giperlink.customer.controller_models.request.CustomerFullInfoRequest;
import ru.espada.giperlink.customer.controller_models.request.CustomerShortInfoRequest;
import ru.espada.giperlink.customer.controller_models.request.RemovePageSessionRequest;
import ru.espada.giperlink.customer.controller_models.response.CustomerBaseInfoResponse;
import ru.espada.giperlink.customer.controller_models.response.CustomerFullInfoResponse;
import ru.espada.giperlink.customer.controller_models.response.CustomerShortInfoResponse;
import ru.espada.giperlink.geolocation.GeolocationService;
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
    private final GeolocationService geolocationService;
    private final ConnectedServiceService connectedServiceService;
    private final AppealService appealService;
    /**
     * Поле содержит активные сессии зпросов пользователей из бд
     * Ключ - id запросов (0 - base info, 1 - short info)
     * Ключ - id пользователя
     * Значение - текущая страница
     */
    private Map<Integer, Map<Long, Integer>> activePageSessions;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           UserService userService,
                           CustomerServiceConfig customerServiceConfig,
                           GeolocationService geolocationService,
                           ConnectedServiceService connectedServiceService,
                           AppealService appealService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.customerServiceConfig = customerServiceConfig;
        this.geolocationService = geolocationService;
        this.connectedServiceService = connectedServiceService;
        this.appealService = appealService;

        // инициализация активных сессий
        activePageSessions = new HashMap<>();
        activePageSessions.put(0, new HashMap<>());
        activePageSessions.put(1, new HashMap<>());
    }

    /**
     * @param request запрос из API
     * @return базовая информация о клиенте (id, ФИО)
     * @throws ru.espada.giperlink.user.UserException исключение при отсутствии пользователя
     */
    public ResponseEntity<CustomerBaseInfoResponse> getBaseInfo(CustomerBaseInfoRequest request) throws UserException {
        UserEntity user = userService.getUserById(request.getUserId(), request.getLang());

        return ResponseEntity
                .ok()
                .body(CustomerBaseInfoResponse.builder()
                        .id(user.getId())
                        .name(user.getUserPrivate().getName())
                        .surname(user.getUserPrivate().getSurname())
                        .patronymic(user.getUserPrivate().getPatronymic())
                        .build());
    }

    /**
     * @param request запрос из API
     * @return базовая информация о клиентах (id, ФИО)
     * @throws ru.espada.giperlink.user.UserException исключение при отсутствии пользователя
     */
    public ResponseEntity<List<CustomerBaseInfoResponse>> getBaseInfos(CustomerBaseInfoRequest request) throws UserException {
        int page = updatePageSession(0, request.getUserId());
        List<UserEntity> users = userService.getUsers(request.getLang(), page, customerServiceConfig.getPageSize());
        return ResponseEntity
                .ok()
                .body(users.stream().map(user -> CustomerBaseInfoResponse.builder()
                        .id(user.getId())
                        .name(user.getUserPrivate().getName())
                        .surname(user.getUserPrivate().getSurname())
                        .patronymic(user.getUserPrivate().getPatronymic())
                        .build())
                        .toList());
    }

    /**
     * @param request запрос из API
     * @return краткая информация о клиенте (id, номер, ФИО, договор, лицевой счёт, подключенные сервисы)
     */
    public ResponseEntity<CustomerShortInfoResponse> getShortInfo(CustomerShortInfoRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getId()).orElseThrow(() -> new CustomerException("exception.customer.not_found"));
        return ResponseEntity
                .ok()
                .body(CustomerShortInfoResponse.builder()
                        .id(customer.getId())
                        .number(buildCustomerNumber(customer))
                        .name(customer.getUserPrivate().getName())
                        .surname(customer.getUserPrivate().getSurname())
                        .patronymic(customer.getUserPrivate().getPatronymic())
                        .contract(customer.getContracts())
                        .personalAccount(customer.getPersonalAccount())
                        .connectedServices(buildCustomerConnectedServices(customer))
                        .build());
    }

    /**
     * @param request запрос из API
     * @return краткая информация о клиентах (id, номер, ФИО, договор, лицевой счёт, подключенные сервисы)
     */
    public ResponseEntity<List<CustomerShortInfoResponse>> getShortInfos(CustomerShortInfoRequest request) {
        int page = updatePageSession(1, request.getId());
        List<CustomerEntity> customers = getCustomers(page, customerServiceConfig.getPageSize());
        return ResponseEntity
                .ok()
                .body(customers.stream().map(user -> CustomerShortInfoResponse.builder()
                        .id(user.getId())
                        .number(buildCustomerNumber(user))
                        .name(user.getUserPrivate().getName())
                        .surname(user.getUserPrivate().getSurname())
                        .patronymic(user.getUserPrivate().getPatronymic())
                        .contract(user.getContracts())
                        .personalAccount(user.getPersonalAccount())
                        .connectedServices(buildCustomerConnectedServices(user))
                        .build())
                        .toList());
    }

    /**
     * @param request запрос из API
     * @return полная информация о клиенте (id, номер, ФИО, договор, лицевой счёт, подключенные сервисы, адрес, паспортные данные, данные об оборудовании)
     */
    public ResponseEntity<CustomerFullInfoResponse> getFullInfo(CustomerFullInfoRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getId()).orElseThrow(() -> new CustomerException("exception.customer.not_found"));
        return ResponseEntity
                .ok()
                .body(CustomerFullInfoResponse.builder()
                        .customer(customer)
                        .appealList(appealService.getAllUserAppeals12Month(customer))
                        .build());
    }

    public void removePageSession(RemovePageSessionRequest request) {
        activePageSessions.get(0).remove(request.getUserId());
        activePageSessions.get(1).remove(request.getUserId());
    }

    public List<CustomerEntity> getCustomers(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return customerRepository.findAll(pageRequest).toList();
    }

    /**
     * Обновляет счетчик активных сессий
     * @return текущая страница
     */
    private int updatePageSession(int requestType, long userId) {
        if (activePageSessions.get(requestType).containsKey(userId)) {
            activePageSessions.get(requestType).put(userId, activePageSessions.get(requestType).get(userId) + 1);
        } else {
            activePageSessions.get(requestType).put(userId, 0);
        }
        return activePageSessions.get(requestType).get(userId);
    }

    private String buildCustomerNumber(CustomerEntity customer) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(geolocationService.getRegionIdByCity(customer.getCustomerPrivate().getAddress().getCity()));
        stringBuilder.append(customer.getCustomerPrivate().getAddress().getDistrict().charAt(0));
        stringBuilder.append(customer.getNumber());
        return stringBuilder.toString();
    }

    private String buildCustomerConnectedServices(CustomerEntity customer) {
        List<ConnectedServiceEntity> connectedServices = connectedServiceService.getConnectedServices(customer.getId());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < connectedServices.size(); i++) {
            stringBuilder.append(connectedServices.get(i).getServices().getName());
            if (i < connectedServices.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }
}
