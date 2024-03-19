package ru.espada.giperlink.localization;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import ru.espada.giperlink.localization.exception.LocalizationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class LocalizationYAMLReader implements LocalizationReader {

    @Override
    public Map<String, String> readLocalization(File file) {
        return getDataFromFile(file);
    }

    private Map<String, String> getDataFromFile(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            Yaml yaml = new Yaml();
            Map<String, String> messages = yaml.load(inputStream);
            inputStream.close();
            return messages;
        } catch (IOException e) {
            throw new LocalizationException("Yaml localization file with prefix " + file.getName() + " failed to read!", file.getName());
        }
    }
}
