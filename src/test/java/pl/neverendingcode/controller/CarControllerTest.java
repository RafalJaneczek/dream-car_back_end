package pl.neverendingcode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.adapter.VehicleRepositoryImpl;
import pl.neverendingcode.entity.Car;
import pl.neverendingcode.enums.VehicleCondition;
import pl.neverendingcode.enums.car.BodyType;
import pl.neverendingcode.enums.car.EngineType;
import pl.neverendingcode.model.PageResponse;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class CarControllerTest {

    private final String URL_ADD_CAR = "/car/add";
    private final String URL_GET_CARS = "/car/get-all";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleRepositoryImpl<Car> carRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    private static Car getCar_1() {
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

    private static Car getCar_2() {
        return Car.builder()
                .mark("FERRARI")
                .model("ENZO")
                .engineCapacity(5289)
                .enginePower(660)
                .course(25000)
                .vehicleCondition(VehicleCondition.USED)
                .damaged(false)
                .productionYear(2015)
                .price(BigInteger.valueOf(2500000))
                .engineType(EngineType.GAS)
                .bodyType(BodyType.COUPE)
                .numberOfSeats(2).build();
    }

    private static List<Car> getCarsList() {
        return List.of(getCar_1(), getCar_2());
    }

    @Test
    @DisplayName("Controllers POST method should return saved car object with http status 201. Using assertj assertions")
    void should_return_saved_car_object_and_check_using_assertj() throws Exception {
        // given
        Car car = getCar_1();
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(URL_ADD_CAR);

        // when
        MvcResult response = mockMvc.perform(builder.content(mapper.writeValueAsString(car))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        Car responseBody = mapper.readValue(response.getResponse().getContentAsString(), Car.class);
        assertAll(
                () -> assertThat(responseBody).isNotNull(),
                () -> assertThat(responseBody.getCourse()).isEqualTo(car.getCourse()),
                () -> assertThat(responseBody.getId()).isNotNull(),
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

    @Test
    @DisplayName("Controllers POST method should return saved car object with http status 201. Using ResultActions to check expected results")
    void should_return_saved_car_object_and_check_using_ResultActions() throws Exception {
        // given
        Car car = getCar_1();
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(URL_ADD_CAR);

        // when + then
        mockMvc.perform(builder.content(mapper.writeValueAsString(car))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mark").value("LAMBORGHINI"))
                .andExpect(jsonPath("$.model").value("HURACAN"))
                .andExpect(jsonPath("$.engineCapacity").value(4879))
                .andExpect(jsonPath("$.enginePower").value(675))
                .andExpect(jsonPath("$.course").value(1500))
                .andExpect(jsonPath("$.vehicleCondition").value("USED"))
                .andExpect(jsonPath("$.damaged").value(false))
                .andExpect(jsonPath("$.productionYear").value(2021))
                .andExpect(jsonPath("$.price").value(1750000))
                .andExpect(jsonPath("$.engineType").value("GAS"))
                .andExpect(jsonPath("$.bodyType").value("COUPE"))
                .andExpect(jsonPath("$.numberOfSeats").value(2));
    }

    @Test
    @DisplayName("Controllers GET method should return list of car objects with http status 200. Using assertj assertions")
    void should_return_list_of_car_objects_and_check_using_assertj() throws Exception {
        // given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(URL_GET_CARS);
        getCarsList().forEach(car -> carRepository.save(car));
        // when
        MvcResult response = mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        PageResponse<Car> responseBody = mapper.readValue(response.getResponse().getContentAsString(), PageResponse.class);

        // then
        assertAll(
                () -> assertThat(responseBody.totalPages()).isEqualTo(1),
                () -> assertThat(responseBody.vehicles()).isNotNull(),
                () -> assertThat(responseBody.vehicles().size()).isEqualTo(2)
        );

    }

}