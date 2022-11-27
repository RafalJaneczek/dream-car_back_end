package pl.neverendingcode.repository;

import pl.neverendingcode.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    List<Car> findAll();

    Optional<Car> findById(int id);

    Car save(Car car);

    void deleteById(int id);

}
