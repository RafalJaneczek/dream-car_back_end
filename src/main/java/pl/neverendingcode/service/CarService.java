package pl.neverendingcode.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.exception.CarNotFoundException;
import pl.neverendingcode.model.Car;
import pl.neverendingcode.repository.CarRepository;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository repository;

    public ResponseEntity<List<Car>> findCars() {
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<Car> findCar(int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
    }

    public ResponseEntity<Car> saveCar(Car car) {
        Car result = repository.save(car);
        return ResponseEntity.created(URI.create("/car/get/" + result.getId())).body(result);
    }

    public ResponseEntity<?> removeCar(int id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<?> updateCar(int id, Car toUpdate) {
        if (!repository.existsById(id)) {
            throw new CarNotFoundException("Car with id: " + id + " not found");
        }
        repository.findById(id).ifPresent(car -> {
            car.updateFrom(toUpdate);
            repository.save(car);
        });

        return ResponseEntity.noContent().build();
    }

}
