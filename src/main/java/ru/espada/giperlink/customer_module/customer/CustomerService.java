package ru.espada.giperlink.customer_module.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.espada.giperlink.forum_module.appeal.AppealService;
import ru.espada.giperlink.customer_module.customer.config.CustomerServiceConfig;
import ru.espada.giperlink.customer_module.customer.annotation.CustomerSearchFiled;
import ru.espada.giperlink.customer_module.customer.controller_models.request.*;
import ru.espada.giperlink.customer_module.customer.customer_info.connected_service.ConnectedServiceEntity;
import ru.espada.giperlink.customer_module.customer.customer_info.connected_service.ConnectedServiceService;
import ru.espada.giperlink.customer_module.customer.controller_models.response.CustomerBaseInfoResponse;
import ru.espada.giperlink.customer_module.customer.controller_models.response.CustomerFullInfoResponse;
import ru.espada.giperlink.customer_module.customer.controller_models.response.CustomerShortInfoResponse;
import ru.espada.giperlink.customer_module.customer.exception.CustomerException;
import ru.espada.giperlink.customer_module.customer.util_service.CustomerFilterService;
import ru.espada.giperlink.customer_module.customer.util_service.CustomerFindService;
import ru.espada.giperlink.geolocation.GeolocationService;
import ru.espada.giperlink.customer_module.user.UserEntity;
import ru.espada.giperlink.customer_module.user.UserException;
import ru.espada.giperlink.customer_module.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final CustomerFilterService customerFilterService;
    private final CustomerFindService customerFindService;
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
                           AppealService appealService,
                           CustomerFilterService customerFilterService,
                           CustomerFindService customerFindService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.customerServiceConfig = customerServiceConfig;
        this.geolocationService = geolocationService;
        this.connectedServiceService = connectedServiceService;
        this.appealService = appealService;
        this.customerFilterService = customerFilterService;
        this.customerFindService = customerFindService;

        // инициализация активных сессий
        activePageSessions = new HashMap<>();
        activePageSessions.put(0, new HashMap<>());
        activePageSessions.put(1, new HashMap<>());
    }

    /**
     * @param request запрос из API
     * @return базовая информация о клиенте (id, ФИО)
     * @throws UserException исключение при отсутствии пользователя
     */
    public ResponseEntity<CustomerBaseInfoResponse> getBaseInfo(CustomerBaseInfoRequest request) throws UserException {
        UserEntity user = userService.getUserById(request.getUserId(), request.getLang());
        return ResponseEntity
                .ok()
                .body(CustomerBaseInfoResponse.builder()
                        .active(isCustomerActive(user.getId()))
                        .id(user.getId())
                        .name(user.getUserPrivate().getName())
                        .surname(user.getUserPrivate().getSurname())
                        .patronymic(user.getUserPrivate().getPatronymic())
                        .build());
    }

    /**
     * @param request запрос из API
     * @return базовая информация о клиентах (id, ФИО)
     * @throws UserException исключение при отсутствии пользователя
     */
    public ResponseEntity<List<?>> getBaseInfos(CustomerBaseInfoRequest request) throws UserException {
        int page = updatePageSession(0, request.getUserId());
        List<UserEntity> users = userService.getUsers(request.getLang(), page, customerServiceConfig.getPageSize());
        List<CustomerBaseInfoResponse> customerBaseInfoResponses = users.stream().map(user -> CustomerBaseInfoResponse.builder()
                        .active(isCustomerActive(user.getId()))
                        .id(user.getId())
                        .name(user.getUserPrivate().getName())
                        .surname(user.getUserPrivate().getSurname())
                        .patronymic(user.getUserPrivate().getPatronymic())
                        .build())
                .collect(Collectors.toList());
        customerFilterService.filterUsers(customerBaseInfoResponses, request.getFilters());
        return ResponseEntity
                .ok()
                .body(customerBaseInfoResponses);
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
                        .active(isCustomerActive(customer))
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
    public ResponseEntity<List<?>> getShortInfos(CustomerShortInfoRequest request) {
        int page = updatePageSession(1, request.getId());
        List<CustomerEntity> customers = getCustomers(page, customerServiceConfig.getPageSize());
        List<CustomerShortInfoResponse> customerShortInfoResponses = customers.stream().map(user -> CustomerShortInfoResponse.builder()
                        .active(isCustomerActive(user))
                        .id(user.getId())
                        .number(buildCustomerNumber(user))
                        .name(user.getUserPrivate().getName())
                        .surname(user.getUserPrivate().getSurname())
                        .patronymic(user.getUserPrivate().getPatronymic())
                        .contract(user.getContracts())
                        .personalAccount(user.getPersonalAccount())
                        .connectedServices(buildCustomerConnectedServices(user))
                        .build())
                .collect(Collectors.toList());
        customerFilterService.filterUsers(customerShortInfoResponses, request.getFilters());
        return ResponseEntity
                .ok()
                .body(customerShortInfoResponses);
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
                        .active(isCustomerActive(customer))
                        .customer(customer)
                        .appealList(appealService.getAllUserAppeals12Month(customer))
                        .build());
    }

    /**
     * Получает полную информацию о клиентах по значению поля.
     * Поле должно быть помечено аннотацией {@link CustomerSearchFiled}
     * и класс содержащий поле должен быть наследником или наследником наследника {@link UserEntity}
     * @param request объект содержащий информацию поиска
     * @return ResponseEntity где body - список полной информации о клиентах
     */
    public ResponseEntity<?> getCustomersByField(CustomerFullInfoByFieldInfoRequest request) {
        return ResponseEntity
                .ok()
                .body(customerFindService.findAllCustomersByField(request.getField(), request.getValue()));
    }

    /**
     * Получает полную информацию о клиенте по номеру телефона
     * @param number номер телефона клиента
     * @return полная информация о клиенте
     */
    public CustomerEntity getCustomerByNumber(String number) {
        return customerRepository.findCustomerEntityByNumber(number).orElseThrow(() -> new CustomerException("exception.customer.not_found"));
    }

    public void removePageSession(RemovePageSessionRequest request) {
        activePageSessions.get(0).remove(request.getUserId());
        activePageSessions.get(1).remove(request.getUserId());
    }

    private List<CustomerEntity> getCustomers(int pageNumber, int pageSize) {
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

    /**
     * Проверяет активен ли клиент (клиент счтитается активным, когда хотябы один договор не закрыт)
     * @param id id клиента
     * @return статус активности
     */
    private boolean isCustomerActive(long id) {
        return customerRepository
                .findById(id).orElseThrow(() -> new CustomerException("exception.customer.not_found"))
                .getContracts().stream().anyMatch(contract -> contract.getDateOfTermination() == null);
    }

    /**
     * Проверяет активен ли клиент (клиент счтитается активным, когда хотябы один договор не закрыт)
     * @param customer клиент
     * @return статус активности
     */
    private boolean isCustomerActive(CustomerEntity customer) {
        return customer.getContracts().stream().anyMatch(contract -> contract.getDateOfTermination() == null);
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
