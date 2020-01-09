package com.example.price;


import com.example.price.domain.price.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PriceControllerTests {
    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getExistingVehiclePrice() {
        ResponseEntity<Price> response = this.testRestTemplate.getForEntity("http://localhost:" + port + "/services/price?vehicleId=11", Price.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getNonExistingVehiclePrice() {
        ResponseEntity<Price> response = this.testRestTemplate.getForEntity("http://localhost:" + port + "/services/price?vehicleId=25", Price.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
//        assertThat(response., equalTo(HttpStatus.NOT_FOUND));
    }
}
