package org.greeneyed.airportf.service;

import java.io.IOException;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class IPLocatorService {

    private final DatabaseReader databaseReader;

    @Autowired
    public IPLocatorService(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    public CityResponse getLocation(String ip)
            throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        final CityResponse city = databaseReader.city(ipAddress);
        if(city!=null) {
            log.info("Found city {}/{} for given ip {}", city.getCountry().getName(), city.getCity().getName(), ipAddress.getHostAddress());
        }
        return city;
    }
}
