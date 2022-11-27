package pl.neverendingcode.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.model.Car;
import pl.neverendingcode.repository.CarRepository;

@Repository
public interface SqlCarRepository extends CarRepository, JpaRepository<Car, Integer> {
}
