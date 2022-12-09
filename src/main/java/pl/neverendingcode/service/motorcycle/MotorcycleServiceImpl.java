package pl.neverendingcode.service.motorcycle;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.adapter.MotorcycleRepositoryImpl;
import pl.neverendingcode.exception.MotorcycleNotFoundException;
import pl.neverendingcode.model.Motorcycle;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MotorcycleServiceImpl implements MotorcycleService {

    private final MotorcycleRepositoryImpl motorcycleRepository;

    @Override
    public ResponseEntity<List<Motorcycle>> findAll() {
        return ResponseEntity.ok().body(motorcycleRepository.findAll());
    }

    @Override
    public ResponseEntity<Motorcycle> findMotorcycle(int id) {
        return motorcycleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MotorcycleNotFoundException("Motorcycle with id: " + id + " not found"));
    }

    @Override
    public ResponseEntity<Motorcycle> saveMotorcycle(Motorcycle motorcycle) {
        return ResponseEntity.ok().body(motorcycleRepository.save(motorcycle));
    }

    @Override
    public ResponseEntity<?> removeMotorcycle(int id) {
        motorcycleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Motorcycle> updateMotorcycle(int id, Motorcycle toUpdate) {
        Motorcycle result = motorcycleRepository.findById(id).map(motorcycle -> {
            motorcycle.updateFrom(toUpdate);
            return motorcycleRepository.save(motorcycle);
        }).orElseThrow(() -> new MotorcycleNotFoundException("Car with id: " + id + " not found"));
        log.info("Motorcycle with id: " + toUpdate.getId() + " has been updated successfully");
        return ResponseEntity.ok().body(result);
    }

}
