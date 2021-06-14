package org.greeneyed.airportf.model.api;

import java.util.List;

import org.greeneyed.airportf.model.RelativeDistance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class APIAirportsResponse {
    APILocation location;
    List<RelativeDistance> distances;
}
