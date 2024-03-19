package ru.espada.giperlink.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.espada.giperlink.localization.exception.LocalizationException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author espada
 * @since 19.03.2024
 *
 * Управляет локализацией, для удобства работы исключений
 * Предполагается использование yaml формата файлов
 *
 * Условия локализации:
 * 1. Все файлы локализации сохраняются в папке localization/
 * 2. Все файлы локализации имеют формат {lang}.yaml
 * 3. Стандартным языком является русский ru.yaml
 */
@Service
public class LocalizationService {

    public static final String DEFAULT_LOCALE = "ru";
    private static final String DEFAULT_FOLDER = "localization/";
    private Map<String, Map<String, ?>> localizedMessages;


    public LocalizationService(@Autowired LocalizationYAMLReader localizationYAMLReader) {
        localizedMessages = new HashMap<>();
        readAllLocalizationFiles(localizationYAMLReader);
    }

    public String getLocalizedMessage(String code) {
        try {
            String[] path = code.split("\\.");
            String result = getMessageFromChain(path, localizedMessages.get(DEFAULT_LOCALE), 0);
            if (result == null) {
                throw new LocalizationException("No value by code: " + code, DEFAULT_LOCALE);
            }
            return result;
        }catch (Exception e) {
            return "";
        }
    }

    public String getLocalizedMessage(String language, String code) {
        try {
            String[] path = code.split("\\.");
            String result = getMessageFromChain(path, localizedMessages.get(language), 0);
            if (result == null) {
                throw new LocalizationException("No value by code: " + code, language);
            }
            return result;
        } catch (NullPointerException e) {
            throw new LocalizationException("No language with id " + language, language);
        }
    }

    private String getMessageFromChain(String[] path, Map<String, ?> map, int i) {
        if (map.get(path[i]) instanceof Map) {
            return getMessageFromChain(path, (Map<String, ?>) map.get(path[i]), i + 1);
        }
        return (String) map.get(path[i]);
    }

    private void readAllLocalizationFiles(LocalizationReader localizationReader) {
        String path = DEFAULT_FOLDER;
        try {
            File file = new File(path);
            for (File localizationFile : Objects.requireNonNull(file.listFiles())) {
                Map<String, String> messages = localizationReader.readLocalization(localizationFile);
                String lang = localizationFile.getName().split("\\.")[0];
                localizedMessages.put(lang, messages);
            }
        } catch (LocalizationException | NullPointerException e) {
            throw new LocalizationException("No path " + path + e.getMessage(), DEFAULT_LOCALE);
        }
    }
}
