package pl.neverendingcode.repository.motorcycle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.adapter.MotorcycleRepositoryImpl;
import pl.neverendingcode.model.Motorcycle;

@Repository
public interface MotorcycleRepository extends MotorcycleRepositoryImpl, JpaRepository<Motorcycle, Integer> {
}
