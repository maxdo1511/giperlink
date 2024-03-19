package ru.espada.giperlink.geolocation.model;

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
    private int districtId;
    private int regionId;
    private String coordinates;

}
