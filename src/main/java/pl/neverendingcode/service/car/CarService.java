package pl.neverendingcode.service.car;

import org.springframework.http.ResponseEntity;
import pl.neverendingcode.model.Car;
import pl.neverendingcode.model.PageResponse;

public interface CarService {
    ResponseEntity<PageResponse<Car>> findCars(Integer pageNo, Integer pageSize, String sortBy);

    ResponseEntity<Car> findCar(int id);

    ResponseEntity<Car> saveCar(Car car);

    ResponseEntity<?> removeCar(int id);

    ResponseEntity<Car> updateCar(int id, Car toUpdate);
}
