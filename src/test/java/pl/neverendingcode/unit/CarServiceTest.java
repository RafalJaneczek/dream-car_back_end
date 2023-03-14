package pl.neverendingcode.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.neverendingcode.vehicle.contract.VehicleRepository;
import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.car.exception.CarNotFoundException;
import pl.neverendingcode.vehicle.model.PageResponse;
import pl.neverendingcode.vehicle.car.service.CarService;
import pl.neverendingcode.vehicle.service.CarPhotoService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static pl.neverendingcode.helper.CarProviderHelper.getCarsList;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private VehicleRepository<Car> carRepository;

    private CarService carService;
    private CarPhotoService photoService;
    @BeforeEach
    public void setUp() {
        this.carService = new CarService(carRepository, photoService);
    }

    @Test
    @DisplayName("When findCars it should return a list of Car objects")
    void should_return_list_of_car_objects() {
        // given
        Page<Car> carPage = new PageImpl<>(getCarsList());
        Pageable paging = PageRequest.of(1, 2, Sort.by("id"));

        // when
        when(carRepository.findAll(paging)).thenReturn(carPage);
        ResponseEntity<PageResponse<Car>> response = carService.findAll(1, 2, "id");

        // then
        assertThat(response).isInstanceOf(ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName(("Should throw CarNotFoundException when cannot find car with provided id"))
    void should_throw_CarNotFoundException_when_car_with_given_id_do_not_exists() {
        // given
        int id = anyInt();

        // when
        when(carRepository.findById(id)).thenThrow(new CarNotFoundException("Car with id: " + id + " not found"));
        var exception = catchThrowable(() -> carService.findById(id));

        // then
        assertThat(exception)
                .isInstanceOf(CarNotFoundException.class)
                .hasMessageContaining("Car with id: ");
    }

    @Test
    @DisplayName("Should find and return car object by given id")
    void should_find_and_return_car_object_by_given_id() {
        // given
        int id = anyInt();

        // when
        when(carRepository.findById(id)).thenReturn(Optional.of(new Car()));
        var response = carService.findById(id);

        // then
        assertAll(
                () -> assertThat(response.getBody()).isNotNull(),
                () -> assertThat(response.getBody()).isInstanceOf(Car.class),
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
        );

    }

//    @Test
//    @DisplayName("Should return saved car, HttpStatus 201 and URI location of saved object")
//    void should_return_saved_car() {
//        // given + when
//        given(carRepository.save(any(Car.class))).willReturn(getCar_1());
//        ResponseEntity<Car> response = carService.save(getCar_1());
//        Car responseBody = response.getBody();
//
//        // them
//        assertAll(
//                () -> assertThat(responseBody).isNotNull(),
//                () -> assertThat(responseBody).isInstanceOf(Car.class),
//                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED),
//                () -> assertThat(response.getHeaders().get("Location").get(0)).isEqualTo("/car/get/" + responseBody.getId())
//        );
//
//    }

}