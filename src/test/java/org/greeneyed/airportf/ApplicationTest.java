package org.greeneyed.airportf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.maxmind.geoip2.WebServiceClient;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTest {

    @MockBean
    private WebServiceClient webServiceClient;

    @Test
    void contextLoads() {
    }

}
