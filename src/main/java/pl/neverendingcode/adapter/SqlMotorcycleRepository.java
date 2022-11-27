package pl.neverendingcode.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.model.Motorcycle;
import pl.neverendingcode.repository.MotorcycleRepository;

@Repository
public interface SqlMotorcycleRepository extends MotorcycleRepository, JpaRepository<Motorcycle, Integer> {
}
