package org.greeneyed.airportf.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class APICoordinate {
    public enum Direction {
        N, S, E, W;
    }

    private int degrees;
    private int minutes;
    private int seconds;
    private Direction direction;
    private double decimalDegrees;
    private double decimalRadians;
}
