package com.udacity.vehicles.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Implements testing of the CarController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CarControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllVehiclesTest() {
        ResponseEntity<Object> response = this.testRestTemplate.getForEntity("http://localhost:" + port + "/cars", Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void saveNewVehicleTest() {
        Car newCar = new Car();
        newCar.setCondition(Condition.NEW);
        newCar.setDetails(new Details() {
            {
                setBody("metal");
                setModel("2019");
                setManufacturer(new Manufacturer(20, "Honda"));
                setFuelType("petrol");
            }
        });
        newCar.setLocation(new Location(23.0, 44.5));

        ResponseEntity<Car> response = this.testRestTemplate.postForEntity("http://localhost:" + port + "/cars", newCar, Car.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertThat(response.getBody(), notNullValue(Car.class));
    }

    @Test
    public void updateVehicleTest(){
        Car newCar = new Car();
        newCar.setCondition(Condition.NEW);
        newCar.setDetails(new Details() {
            {
                setBody("metal");
                setModel("2019");
                setManufacturer(new Manufacturer(20, "Honda"));
                setFuelType("petrol");
            }
        });
        newCar.setLocation(new Location(23.0, 44.5));

        ResponseEntity<Car> response = this.testRestTemplate.postForEntity("http://localhost:" + port + "/cars", newCar, Car.class);
        newCar = response.getBody();

        // updating the locations
        newCar.setLocation(new Location(55.0, 77.56));
        this.testRestTemplate.put("http://localhost:" + port + "/cars/" + newCar.getId(), newCar);

        ResponseEntity<Car> updatedResponse = this.testRestTemplate.getForEntity("http://localhost:" + port + "/cars/" + newCar.getId(), Car.class);
        Car updatedResponseBody = updatedResponse.getBody();
        assertThat(updatedResponseBody, notNullValue());
        assertThat(updatedResponseBody.getLocation().getLat(), equalTo(newCar.getLocation().getLat()));
        assertThat(updatedResponseBody.getLocation().getLon(), equalTo(newCar.getLocation().getLon()));
    }

    @Test
    public void deleteVehicleTest(){
        Car newCar = new Car();
        newCar.setCondition(Condition.NEW);
        newCar.setDetails(new Details() {
            {
                setBody("metal");
                setModel("2019");
                setManufacturer(new Manufacturer(20, "Honda"));
                setFuelType("petrol");
            }
        });
        newCar.setLocation(new Location(23.0, 44.5));

        ResponseEntity<Car> response = this.testRestTemplate.postForEntity("http://localhost:" + port + "/cars", newCar, Car.class);
        newCar = response.getBody();

        // deleting the vehicle
        this.testRestTemplate.delete("http://localhost:" + port + "/cars/" + newCar.getId());

        ResponseEntity<Car> newGetResponse = this.testRestTemplate.getForEntity("http://localhost:" + port + "/cars/" + newCar.getId(), Car.class);
        assertThat(newGetResponse.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }
}