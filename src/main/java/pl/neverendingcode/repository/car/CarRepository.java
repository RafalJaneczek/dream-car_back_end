package pl.neverendingcode.repository.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.adapter.CarRepositoryImpl;
import pl.neverendingcode.model.Car;

@Repository
public interface CarRepository extends CarRepositoryImpl, JpaRepository<Car, Integer> {
}
