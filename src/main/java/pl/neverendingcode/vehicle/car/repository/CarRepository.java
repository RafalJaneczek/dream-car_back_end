package pl.neverendingcode.vehicle.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.vehicle.factory.VehicleRepositoryFactory;
import pl.neverendingcode.vehicle.car.entity.Car;

@Repository
public interface CarRepository extends VehicleRepositoryFactory<Car>, JpaRepository<Car, Integer> {
}
