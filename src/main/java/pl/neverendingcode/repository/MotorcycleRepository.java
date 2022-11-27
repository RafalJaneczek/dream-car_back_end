package pl.neverendingcode.repository;

import pl.neverendingcode.model.Motorcycle;

import java.util.List;
import java.util.Optional;

public interface MotorcycleRepository {

    List<Motorcycle> findAll();

    Optional<Motorcycle> findById(int id);

    Motorcycle save(Motorcycle motorcycle);

    void deleteById(int id);

}
