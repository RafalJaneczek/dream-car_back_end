package pl.neverendingcode.vehicle.motorcycle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.vehicle.adapter.VehicleRepositoryImpl;
import pl.neverendingcode.vehicle.motorcycle.entity.Motorcycle;

@Repository
public interface MotorcycleRepository extends VehicleRepositoryImpl<Motorcycle>, JpaRepository<Motorcycle, Integer> {
}
