package org.greeneyed.airportf.mappers;

import org.greeneyed.airportf.model.RelativeDistance;
import org.greeneyed.airportf.model.api.APILocation;
import org.greeneyed.airportf.model.api.APIRelativeDistance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.maxmind.geoip2.model.CityResponse;

@Mapper(componentModel = "spring")
public abstract class MapperService {

    @Mapping(target = "country", source = "cityResponse.country.name")
    @Mapping(target = "city", source = "cityResponse.city.name")
    public abstract APILocation from(CityResponse cityResponse);

    public abstract APIRelativeDistance from(RelativeDistance relativeDistance);
}
