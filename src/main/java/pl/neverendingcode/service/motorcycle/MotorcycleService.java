package pl.neverendingcode.service.motorcycle;

import org.springframework.http.ResponseEntity;
import pl.neverendingcode.entity.Motorcycle;
import pl.neverendingcode.model.PageResponse;

public interface MotorcycleService {
    ResponseEntity<PageResponse<Motorcycle>> findMotorcycles(Integer pageNo, Integer pageSize, String sortBy);

    ResponseEntity<Motorcycle> findMotorcycle(int id);

    ResponseEntity<Motorcycle> saveMotorcycle(Motorcycle Motorcycle);

    ResponseEntity<?> removeMotorcycle(int id);

    ResponseEntity<Motorcycle> updateMotorcycle(int id, Motorcycle toUpdate);
}
