package pl.neverendingcode.service.car;

import org.springframework.http.ResponseEntity;
import pl.neverendingcode.model.Car;

import java.util.List;

public interface CarService {
    ResponseEntity<List<Car>> findCars();

    ResponseEntity<Car> findCar(int id);

    ResponseEntity<Car> saveCar(Car car);

    ResponseEntity<?> removeCar(int id);

    ResponseEntity<Car> updateCar(int id, Car toUpdate);
}
