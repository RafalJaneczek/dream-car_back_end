package pl.neverendingcode.service.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import pl.neverendingcode.adapter.VehicleRepositoryImpl;
import pl.neverendingcode.exception.CarNotFoundException;
import pl.neverendingcode.entity.Car;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private VehicleRepositoryImpl<Car> carRepository;

    private CarServiceImpl carService;


    @BeforeEach
    public void setUp() {
        this.carService = new CarServiceImpl(carRepository);
    }

    @Test
    @DisplayName(("Should throw CarNotFoundException when cannot find car with provided id"))
    void should_throw_CarNotFoundException_when_car_with_given_id_do_not_exists() {
        // given
        int id = anyInt();
        // when
        when(carRepository.findById(id)).thenThrow(new CarNotFoundException("Car with id: " + id + " not found"));
//        var exception = catchThrowable(() -> carService.findCar(id));
        // then
//        assertThat(exception)
//                .isInstanceOf(CarNotFoundException.class)
//                .hasMessageContaining("Car with id: ");
    }

    @Test
    @DisplayName("Should find and return car object by given id")
    void should_find_and_return_car_object_by_given_id() {
        // given
        int id = anyInt();
//
//        // when
//        when(carRepository.findById(id)).thenReturn(Optional.of(new Car()));
//        var response = carService.findCar(id);
//
//        // then
//        assertThat(response.getBody()).isInstanceOf(Car.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

//    @Test
//    @DisplayName("When findCars it should return a list of Car objects")
//    void should_return_list_of_car_objects() {
//        // given
//        List<Car> cars = mock(List.class);
//        // when
//        when(carRepository.findAll()).thenReturn(cars);
//        ResponseEntity<List<Car>> response = carService.findCars();
//
//        // then
//        assertThat(response).isInstanceOf(ResponseEntity.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
}