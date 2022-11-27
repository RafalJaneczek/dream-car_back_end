package pl.neverendingcode.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.exception.MotorcycleNotFoundException;
import pl.neverendingcode.model.Motorcycle;
import pl.neverendingcode.adapter.SqlMotorcycleRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MotorcycleService {

    private final SqlMotorcycleRepository repository;

    public ResponseEntity<List<Motorcycle>> findAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    public ResponseEntity<Motorcycle> findMotorcycle(int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MotorcycleNotFoundException("Motorcycle with id: " + id + " not found"));
    }

    public ResponseEntity<Motorcycle> saveMotorcycle(Motorcycle motorcycle) {
        return ResponseEntity.ok().body(repository.save(motorcycle));
    }

    public ResponseEntity<?> removeMotorcycle(int id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<Motorcycle> updateMotorcycle(int id, Motorcycle toUpdate) {
        Motorcycle result = repository.findById(id).map(motorcycle -> {
            motorcycle.updateFrom(toUpdate);
            return repository.save(motorcycle);
        }).orElseThrow(() -> new MotorcycleNotFoundException("Car with id: " + id + " not found"));
        log.info("Motorcycle with id: " + toUpdate.getId() + " has been updated successfully");
        return ResponseEntity.ok().body(result);
    }

}
