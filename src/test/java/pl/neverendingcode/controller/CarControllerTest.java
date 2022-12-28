package pl.neverendingcode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.adapter.VehicleRepositoryImpl;
import pl.neverendingcode.entity.Car;
import pl.neverendingcode.enums.VehicleCondition;
import pl.neverendingcode.enums.car.BodyType;
import pl.neverendingcode.enums.car.EngineType;
import pl.neverendingcode.repository.car.CarRepository;
import pl.neverendingcode.service.car.CarServiceImpl;

import java.math.BigInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleRepositoryImpl<Car> carRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    static Car getCar() {
        return Car.builder()
                .mark("LAMBORGHINI")
                .model("HURACAN")
                .engineCapacity(4879)
                .enginePower(675)
                .course(1500)
                .vehicleCondition(VehicleCondition.USED)
                .damaged(false)
                .productionYear(2021)
                .price(BigInteger.valueOf(1750000))
                .engineType(EngineType.GAS)
                .bodyType(BodyType.COUPE)
                .numberOfSeats(2).build();
    }

    @Test
    @Transactional
    void should_return_saved_car_object() throws Exception {
        // given
        Car car = getCar();

        // when
        carRepository.save(car);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/car/add")
                        .content(mapper.writeValueAsString(car))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        // then
        Car responseBody = mapper.readValue(response.getResponse().getContentAsString(), Car.class);
        assertAll(
                () -> assertThat(responseBody).isNotNull(),
                () -> assertThat(responseBody.getCourse()).isEqualTo(car.getCourse()),
                () -> assertThat(responseBody.getId()).isEqualTo(car.getId()),
                () -> assertThat(responseBody.isDamaged()).isEqualTo(car.isDamaged()),
                () -> assertThat(responseBody.getEngineCapacity()).isEqualTo(car.getEngineCapacity()),
                () -> assertThat(responseBody.getEnginePower()).isEqualTo(car.getEnginePower()),
                () -> assertThat(responseBody.getMark()).isEqualTo(car.getMark()),
                () -> assertThat(responseBody.getModel()).isEqualTo(car.getModel()),
                () -> assertThat(responseBody.getPrice()).isEqualTo(car.getPrice()),
                () -> assertThat(responseBody.getProductionYear()).isEqualTo(car.getProductionYear()),
                () -> assertThat(responseBody.getVehicleCondition()).isEqualTo(car.getVehicleCondition()),
                () -> assertThat(responseBody.getEngineType()).isEqualTo(car.getEngineType()),
                () -> assertThat(responseBody.getNumberOfSeats()).isEqualTo(car.getNumberOfSeats()),
                () -> assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value())
        );

    }

}