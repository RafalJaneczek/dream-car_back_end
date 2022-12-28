package pl.neverendingcode.repository.motorcycle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.adapter.VehicleRepositoryImpl;
import pl.neverendingcode.entity.Motorcycle;

@Repository
public interface MotorcycleRepository extends VehicleRepositoryImpl<Motorcycle>, JpaRepository<Motorcycle, Integer> {
}
