package pl.neverendingcode.adapter;

import pl.neverendingcode.model.Motorcycle;

import java.util.List;
import java.util.Optional;

public interface MotorcycleRepositoryImpl {

    List<Motorcycle> findAll();

    Optional<Motorcycle> findById(int id);

    Motorcycle save(Motorcycle motorcycle);

    void deleteById(int id);
}
