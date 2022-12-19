package pl.neverendingcode.service.motorcycle;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.adapter.VehicleRepositoryImpl;
import pl.neverendingcode.exception.MotorcycleNotFoundException;
import pl.neverendingcode.model.Motorcycle;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MotorcycleServiceImpl implements MotorcycleService {

    private final VehicleRepositoryImpl<Motorcycle> motorcycleRepository;

    public ResponseEntity<List<Motorcycle>> findMotorcycles(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Motorcycle> pageResult = motorcycleRepository.findAll(paging);

        if (pageResult.hasContent()) {
            return ResponseEntity.ok(pageResult.getContent());
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
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
