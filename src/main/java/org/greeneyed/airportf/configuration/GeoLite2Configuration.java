package org.greeneyed.airportf.configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

import com.maxmind.geoip2.WebServiceClient;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "geoip2")
@Validated
@Data
@Profile("!test")
public class GeoLite2Configuration {

    @NotNull
    private Integer accountId;

    @NotBlank
    private String licenseKey;

    @Bean
    public WebServiceClient buildWebServiceClient() {
        return new WebServiceClient.Builder(accountId, licenseKey).host("geolite.info").build();
    }
}
