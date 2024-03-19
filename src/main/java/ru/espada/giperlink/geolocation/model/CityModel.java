package ru.espada.giperlink.geolocation.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityModel {

    private String name;
    @JsonSetter("district_id")
    private int districtId;
    @JsonSetter("region_id")
    private int regionId;
    private String coordinates;

}
