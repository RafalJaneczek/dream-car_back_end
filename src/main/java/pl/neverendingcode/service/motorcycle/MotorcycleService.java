package pl.neverendingcode.service.motorcycle;

import org.springframework.http.ResponseEntity;
import pl.neverendingcode.model.Motorcycle;

import java.util.List;

public interface MotorcycleService {
    ResponseEntity<List<Motorcycle>> findAll();

    ResponseEntity<Motorcycle> findMotorcycle(int id);

    ResponseEntity<Motorcycle> saveMotorcycle(Motorcycle Motorcycle);

    ResponseEntity<?> removeMotorcycle(int id);

    ResponseEntity<Motorcycle> updateMotorcycle(int id, Motorcycle toUpdate);
}
