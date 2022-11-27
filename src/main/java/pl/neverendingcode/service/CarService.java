package pl.neverendingcode.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.exception.CarNotFoundException;
import pl.neverendingcode.model.Car;
import pl.neverendingcode.adapter.SqlCarRepository;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final SqlCarRepository repository;

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
    public ResponseEntity<Car> updateCar(int id, Car toUpdate) {
        Car result = repository.findById(id).map(car -> {
            car.updateFrom(toUpdate);
            return repository.save(car);
        }).orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
        log.info("Car with id: " + toUpdate.getId() + " has been updated successfully");
        return ResponseEntity.ok().body(result);
    }

}
