package org.greeneyed.airportf.configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.greeneyed.airportf.mappers.MapperService;
import org.greeneyed.airportf.model.Airport;
import org.greeneyed.airportf.service.AirportLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AirportLocatorConfiguration {
    // Source: http://openflights.org/data.html
    private final static String COUNTRIES_FILE_NAME = "data/countries.csv";
    private final static String AIRPORT_FILE_NAME = "data/airports.csv";

    @Bean
    public AirportLocator buildAirportLocator(MapperService mapperService) {
        log.info("Building country-codes list");
        Map<String, String> countries = new HashMap<>();
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(COUNTRIES_FILE_NAME);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                countries.put(parts[0], parts[1]);
            }
        } catch (Exception e) {
            log.error("Error initialising countries from file", e);
        }
        log.info("Building airport locator...");
        Map<Integer, Airport> airports = new HashMap<>();
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(AIRPORT_FILE_NAME);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Airport airport = new Airport(line);
                if (countries.containsKey(airport.getCountry())) {
                    airport.setCountryCode(countries.get(airport.getCountry()));
                } else {
                    log.debug("No country code found for {}", airport.getCountry());
                }

                airports.put(airport.getId(), airport);
            }
        } catch (Exception e) {
            log.error("Error initialising airports from file", e);
        }
        log.info("APIAirport locator configured with {} airports.", airports.size());
        return new AirportLocator(airports, countries, mapperService);
    }
}
