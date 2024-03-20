package ru.espada.giperlink.customer_module.customer.annotation;

import ru.espada.giperlink.customer_module.customer.CustomerEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для поиска клиентов
 * Аннотированное поле является ключём для поиска
 * @See {@link CustomerEntity}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomerSearchFiled {

    /**
     * Устанавливает что поисковой запрос может содержать несколько значений
     * @return
     */
    boolean many() default false;

}
