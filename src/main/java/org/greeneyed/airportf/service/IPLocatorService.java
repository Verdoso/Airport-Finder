package org.greeneyed.airportf.service;

import java.io.IOException;
import java.net.InetAddress;

import org.springframework.stereotype.Service;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class IPLocatorService {

    private final WebServiceClient webServiceClient;

    public CityResponse getLocation(String ip)
            throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        final CityResponse city = webServiceClient.city(ipAddress);
        if(city!=null) {
            log.debug("Found city {}/{} for given ip {}", city.getCountry().getName(), city.getCity().getName(), ipAddress.getHostAddress());
        }
        return city;
    }
}
