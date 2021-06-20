package org.greeneyed.airportf.service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.greeneyed.airportf.mappers.MapperService;
import org.greeneyed.airportf.model.Airport;
import org.greeneyed.airportf.model.GeoPoint;
import org.greeneyed.airportf.model.RelativeDistance;
import org.greeneyed.airportf.model.api.APIAirportsResponse;

import com.maxmind.geoip2.model.CityResponse;

import lombok.Data;

@Data
public class AirportLocator {

    private final Map<Integer, Airport> airports;
    private final Map<String, String> countries;
    private final MapperService mapperService;

    public String getCountryCode(String countryName) {
        return countries.get(countryName);
    }

    public APIAirportsResponse getClosestAirports(CityResponse cityResponse, int maxAirports, long limitKm,
            boolean withIataCode) {
        APIAirportsResponse apiAirportsResponse = getClosestAirports(
                new GeoPoint(cityResponse.getLocation().getLatitude(), cityResponse.getLocation().getLongitude()),
                maxAirports, limitKm, withIataCode);
        apiAirportsResponse.setLocation(mapperService.from(cityResponse));
        return apiAirportsResponse;
    }

    public APIAirportsResponse getClosestAirports(GeoPoint position, int maxAirports, long limitKm,
            boolean withIataCode) {
        APIAirportsResponse apiAirportsResponse = new APIAirportsResponse();
        // Find the relative distances from the given position to the airports
        // in the system, ordered
        Stream<Airport> stream = airports.values().stream();
        if (withIataCode) {
            stream = stream.filter(Airport::isIncludingIataFaaCode);
        }
        // @formatter:off
        apiAirportsResponse.setDistances(stream
                .map(airport -> new RelativeDistance(position, airport))
                .filter(relativeDistance -> relativeDistance.getDistance().getKilometers() < limitKm)
                .sorted()
                .limit(maxAirports)
                .map(mapperService::from)
                .collect(Collectors.toList())
                );
        // @formatter:on
        return apiAirportsResponse;
    }
}
