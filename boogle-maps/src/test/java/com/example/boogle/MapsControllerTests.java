package com.example.boogle;

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
public class MapsControllerTests {

    @LocalServerPort
    int port;

@Autowired
    private TestRestTemplate testRestTemplate;

@Test
public void getAddress(){

    ResponseEntity<Address> response=this.testRestTemplate.getForEntity("http://localhost:"+port+"/maps?lat=23&lon=45",Address.class);

    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
}
}
