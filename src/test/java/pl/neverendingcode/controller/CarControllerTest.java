package pl.neverendingcode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.vehicle.factory.VehicleRepositoryFactory;
import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.car.exception.CarNotFoundException;
import pl.neverendingcode.vehicle.model.PageResponse;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyInt;
import static pl.neverendingcode.helper.CarProviderHelper.getCar_1;
import static pl.neverendingcode.helper.CarProviderHelper.getCarsList;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleRepositoryFactory<Car> carRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Controllers POST method should return saved car object with http status 201")
    void should_return_saved_car_object_with_http_status_201() throws Exception {
        // given
        Car car = getCar_1();
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/car/add");

        // when
        MvcResult response = mockMvc.perform(builder.content(mapper.writeValueAsString(car))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        Car responseBody = mapper.readValue(response.getResponse().getContentAsString(), Car.class);
        assertAll(
                () -> assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.getResponse().getHeader("Location")).isEqualTo("/car/get/" + responseBody.getId()),
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
                () -> assertThat(responseBody.getNumberOfSeats()).isEqualTo(car.getNumberOfSeats())
        );
    }

    @Test
    @DisplayName("Should return updated car object with 200 HttpStatus when send PUT method controller to update car object data")
    void should_return_updated_car_object() throws Exception {
        // given
        Car car = getCar_1();
        final int course = 45000;

        car = carRepository.save(car);
        car.setCourse(course);
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/car/update/" + car.getId());

        // when
        MvcResult response = mockMvc.perform(builder.content(mapper.writeValueAsString(car))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Car responseBody = mapper.readValue(response.getResponse().getContentAsString(), Car.class);

        // then
        assertAll(
                () -> assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(responseBody).isNotNull(),
                () -> assertThat(responseBody.getCourse())
                        .isNotNull()
                        .isEqualTo(course)
        );
    }

    @Test
    @DisplayName("Should remove car object by given id and return empty response object with 204 HttpStatus")
    void should_delete_car_object_by_given_id() throws Exception {
        // given
        getCarsList().forEach(car -> carRepository.save(car));
        Pageable paging = PageRequest.of(0, 2, Sort.by("id"));
        Page<Car> pageResult = carRepository.findAll(paging);
        Car toRemove = pageResult.get().toList().get(0);

        final MockHttpServletRequestBuilder removeBuilder = MockMvcRequestBuilders.delete("/car/remove/" + toRemove.getId());
        final MockHttpServletRequestBuilder getAllBuilder = MockMvcRequestBuilders.get("/car/get-all");

        // when
        MvcResult responseAfterRemove = mockMvc.perform(removeBuilder.contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        MvcResult responseAfterGetAll = mockMvc.perform(getAllBuilder.contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        PageResponse<Car> getAllResponseBody = mapper.readValue(responseAfterGetAll.getResponse().getContentAsString(), PageResponse.class);

        // then
        assertAll(
                () -> assertThat(responseAfterRemove.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(responseAfterRemove.getResponse().getContentAsString()).isEqualTo(""),
                () -> assertThat(getAllResponseBody.vehicles().size())
                        .isNotNull()
                        .isEqualTo(1),
                () -> assertThat(getAllResponseBody.vehicles().get(0).getMark()).isEqualTo("FERRARI")
        );
    }


    @Test
    @DisplayName("Controllers GET method should return list of car objects with http status 200")
    void should_return_list_of_car_objects_with_http_status_200() throws Exception {
        // given
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/car/get-all");
        getCarsList().forEach(car -> carRepository.save(car));

        // when
        MvcResult response = mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        PageResponse<Car> responseBody = mapper.readValue(response.getResponse().getContentAsString(), PageResponse.class);

        // then
        assertAll(
                () -> assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(responseBody.totalPages()).isEqualTo(1),
                () -> assertThat(responseBody.vehicles()).isNotNull(),
                () -> assertThat(responseBody.vehicles().size()).isEqualTo(2)
        );

    }

    @Test
    @DisplayName("CarController findById method should return CarNotFoundException when try to find nonexistent object")
    void should_return_CarNotFoundException_when_car_not_found() throws Exception {
        // given
        int id = anyInt();
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/car/get/" + id);

        // when + then
        MvcResult response = mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertAll(
                () -> assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response.getResolvedException()).isInstanceOf(CarNotFoundException.class),
                () -> assertThat(Objects.requireNonNull(response.getResolvedException()).getMessage()).isEqualTo("Car with id: " + id + " not found")
        );
    }

    @Test
    @DisplayName("CarController update method should return CarNotFoundException when try to update nonexistent object")
    void should_return_CarNotFoundException_when_update_nonexistent_car() throws Exception {
        // given
        int id = anyInt();
        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/car/update/" + id);

        // when
        MvcResult response = mockMvc.perform(builder.content(mapper.writeValueAsString(getCar_1()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        assertAll(
                () -> assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response.getResolvedException()).isInstanceOf(CarNotFoundException.class),
                () -> assertThat(Objects.requireNonNull(response.getResolvedException()).getMessage()).isEqualTo("Car with id: " + id + " not found")
        );
    }

}