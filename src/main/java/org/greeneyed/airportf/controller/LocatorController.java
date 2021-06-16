package org.greeneyed.airportf.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.greeneyed.airportf.model.Airport;
import org.greeneyed.airportf.model.GeoPoint;
import org.greeneyed.airportf.model.api.APIAirportsResponse;
import org.greeneyed.airportf.service.AirportLocator;
import org.greeneyed.airportf.service.IPLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@RestController
public class LocatorController {
    private final AirportLocator airportLocator;
    private final IPLocatorService ipLocatorService;

    @Autowired
    public LocatorController(AirportLocator airportLocator, IPLocatorService ipLocatorService) {
        this.airportLocator = airportLocator;
        this.ipLocatorService = ipLocatorService;
    }

    // Ej: http://localhost:9999/locate/39.577251/2.633764/
    @RequestMapping(value = "/locate/{latitude}/{longitude}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIAirportsResponse> locate(@PathVariable(value = "latitude") double latitude,
            @PathVariable(value = "longitude") double longitude,
            @RequestParam(value = "limitKm", required = false, defaultValue = "300") long limitKm,
            @RequestParam(value = "max", required = false, defaultValue = "5") int max,
            @RequestParam(value = "withIataCode", required = false, defaultValue = "true") boolean withIataCode) {
        return new ResponseEntity<>(
                airportLocator.getClosestAirports(new GeoPoint(latitude, longitude), max, limitKm, withIataCode),
                HttpStatus.OK);
    }

    // Ej: http://localhost:9999/locate_by_ip/67.218.243.47/
    @RequestMapping(value = "/locate_by_ip/{ip}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIAirportsResponse> locateByIP(@PathVariable(value = "ip") String ip,
            @RequestParam(value = "limitKm", required = false, defaultValue = "300") long limitKm,
            @RequestParam(value = "max", required = false, defaultValue = "5") int max,
            @RequestParam(value = "withIataCode", required = false, defaultValue = "true") boolean withIataCode)
            throws IOException, GeoIp2Exception {
        return new ResponseEntity<>(
                airportLocator.getClosestAirports(ipLocatorService.getLocation(ip), max, limitKm, withIataCode),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Airport>> list(
            @RequestParam(value = "from", required = false, defaultValue = "0") int from,
            @RequestParam(value = "to", required = false, defaultValue = "25") int to) {
        int realFrom = Math.max(0, from);
        int limit = Math.min(Math.abs(to) - realFrom, 25);
        List<Airport> result = airportLocator.getAirports()
                .values()
                .stream()
                .sorted()
                .skip(realFrom)
                .limit(limit)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
