package ru.espada.giperlink.customer_module.customer.util_service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import ru.espada.giperlink.customer_module.customer.CustomerEntity;
import ru.espada.giperlink.customer_module.customer.annotation.CustomerSearchFiled;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author espada
 * @since 20.03.2024
 *
 * Сервис для поиска клиентов по полям из сущностей
 */
@Service
public class CustomerFindService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Находит всех клиентов по полю
     * @param field имя поля по которому искать
     * @param value значение
     * @return список клиентов, если запрос предполагает один ответ, то массив будет состоять из одного элемента
     */
    public List<?> findAllCustomersByField(String field, String value) {
        Field fieldWithAnnotation = findFieldWithCustomerSearchFiledAnnotationAndName(field);
        if (fieldWithAnnotation == null) {
            return new ArrayList<>();
        }
        if (fieldWithAnnotation.getAnnotation(CustomerSearchFiled.class).many()) {
            return findAllCustomersByFieldValue(fieldWithAnnotation, value);
        }
        return List.of(findCustomerByFieldValue(fieldWithAnnotation, value));
    }

    private List<?> findAllCustomersByFieldValue(Field field, String value) {
        Class<?> clazz = field.getDeclaringClass();
        List<?> list = entityManager.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE e." + field.getName() + " = :value", clazz)
                .setParameter("value", value)
                .getResultList();
        return list;
    }

    private CustomerEntity findCustomerByFieldValue(Field field, String value) {
        return null;
    }

    private Field findFieldWithCustomerSearchFiledAnnotationAndName(String name) {
        try {
            for (Field field : findFieldsWithAnnotation(CustomerEntity.class, CustomerSearchFiled.class)) {
                if (field.getName().equals(name)) {
                    return field;
                }
            }
        }catch (Exception e) {
            return null;
        }
        return null;
    }

    private List<Field> findFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Field> result = new ArrayList<>();
        findFieldsWithAnnotationInClass(clazz, annotationClass, result);
        return result;
    }

    private void findFieldsWithAnnotationInClass(Class<?> clazz, Class<? extends Annotation> annotationClass, List<Field> result) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClass)) {
                result.add(field);
            }
        }

        if (clazz.getSuperclass() != null) {
            findFieldsWithAnnotationInClass(clazz.getSuperclass(), annotationClass, result);
        }
    }

}
