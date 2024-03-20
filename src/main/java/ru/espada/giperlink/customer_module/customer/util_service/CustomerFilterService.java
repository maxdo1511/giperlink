package ru.espada.giperlink.customer_module.customer.util_service;

import org.springframework.stereotype.Service;
import ru.espada.giperlink.customer_module.customer.controller_models.response.AbstractCustomerInfoResponse;
import ru.espada.giperlink.customer_module.customer.enums.CustomerFilterEnum;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerFilterService {

    /**
     * Фильтрует список пользователей
     *
     * @param users    список пользователей
     * @param filters  фильтры из enum CustomerFilterEnum (регистер не важен)
     * @return отфильтрованный список пользователей
     */
    public List<?> filterUsers(List<? extends AbstractCustomerInfoResponse> users, List<String> filters) {
        List<CustomerFilterEnum> customerFilters = parseFilters(filters);

        for (CustomerFilterEnum filter : customerFilters) {
            switch (filter) {
                case ACTIVE:
                    users = users.stream().filter(AbstractCustomerInfoResponse::isActive).toList();
                    break;
                case INACTIVE:
                    users = users.stream().filter(user -> !user.isActive()).toList();
                    break;
                case ALL:
                    break;
            }
        }
        return users;
    }

    /**
     * Собирает фильтры в enum с учётом преоритета
     * Если в фильтрах есть несколько операций с одинаковым приоритетом, то остаётся только первый
     *
     * @param filters фильтры
     * @return список фильтров
     */
    private List<CustomerFilterEnum> parseFilters(List<String> filters) {
        return filters.stream()
                .map(String::toUpperCase)
                .map(CustomerFilterEnum::valueOf)
                .sorted((o1, o2) -> o2.getPriority() - o1.getPriority())
                .collect(Collectors.toMap(CustomerFilterEnum::getPriority, o -> o, (o1, o2) -> o1))
                .values()
                .stream()
                .toList();
    }

}
