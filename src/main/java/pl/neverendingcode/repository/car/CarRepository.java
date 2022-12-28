package pl.neverendingcode.repository.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.adapter.VehicleRepositoryImpl;
import pl.neverendingcode.entity.Car;

@Repository
public interface CarRepository extends VehicleRepositoryImpl<Car>, JpaRepository<Car, Integer> {
}
