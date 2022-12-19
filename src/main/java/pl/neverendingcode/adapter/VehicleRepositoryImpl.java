package pl.neverendingcode.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.neverendingcode.model.Vehicle;

import java.util.Optional;

public interface VehicleRepositoryImpl<T extends Vehicle> {

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(int id);

    T save(T motorcycle);

    void deleteById(int id);

}
