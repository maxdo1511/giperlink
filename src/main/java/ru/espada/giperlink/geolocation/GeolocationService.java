package ru.espada.giperlink.geolocation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.espada.giperlink.geolocation.model.CityModel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author espada
 * @since 19.03.2024
 *
 * Сервис для работы с геолокацией
 */
@Service
public class GeolocationService {

    private List<CityModel> cityModels;
    private static final String FILE_PATH = "special_data/cities.json";

    public GeolocationService() {
        loadCities();
    }

    public int getRegionIdByCity(String city) {
        return cityModels.parallelStream().filter(c -> c.getName().equals(city)).findFirst().orElseThrow(() -> new RuntimeException("City not found")).getRegionId();
    }

    private void loadCities() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(FILE_PATH);
            cityModels = objectMapper.readValue(file, new TypeReference<List<CityModel>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
