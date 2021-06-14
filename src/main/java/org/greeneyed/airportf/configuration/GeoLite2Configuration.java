package org.greeneyed.airportf.configuration;

import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.maxmind.geoip2.DatabaseReader;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class GeoLite2Configuration {
    // Source: https://dev.maxmind.com/geoip/geoip2/geolite2/#Databases
    private final static String COUNTRIES_DB_NAME = "data/GeoLite2-City.mmdb";

    @Bean
    public DatabaseReader buildDatabaseReader() {
        log.info("Building GeoLite2 city database");
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(COUNTRIES_DB_NAME)) {
            DatabaseReader dbReader = new DatabaseReader.Builder(inputStream).build();
            log.info("GeoLite2 cities database initialised");
            return dbReader;
        } catch (Exception e) {
            log.error("Error initialising GeoLite2 cities database from file", e);
        }
        return null;
    }
}
