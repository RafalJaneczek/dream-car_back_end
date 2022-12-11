package pl.neverendingcode.adapter;

import pl.neverendingcode.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepositoryImpl<T extends Vehicle> {

    List<T> findAll();

    Optional<T> findById(int id);

    T save(T motorcycle);

    void deleteById(int id);

}
