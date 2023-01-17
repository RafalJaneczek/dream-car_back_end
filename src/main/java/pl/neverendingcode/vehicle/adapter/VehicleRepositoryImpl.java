package pl.neverendingcode.vehicle.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.neverendingcode.vehicle.entity.Vehicle;

import java.util.Optional;

public interface VehicleRepositoryImpl<T extends Vehicle> {

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(int id);

    T save(T motorcycle);

    void deleteById(int id);

}
