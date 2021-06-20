package org.greeneyed.airportf.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class APIAirport {
    @EqualsAndHashCode.Include
    private final Integer id;

    private final String name;

    private final String city;

    private final String country;

    private String countryCode;

    private final String iataFaaCode;

    private final String icaoCode;

    private final APIGeoPoint position;
}
