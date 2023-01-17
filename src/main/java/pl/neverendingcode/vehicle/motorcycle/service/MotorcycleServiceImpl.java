package pl.neverendingcode.vehicle.motorcycle.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.vehicle.adapter.VehicleRepositoryImpl;
import pl.neverendingcode.vehicle.motorcycle.exception.MotorcycleNotFoundException;
import pl.neverendingcode.vehicle.motorcycle.entity.Motorcycle;
import pl.neverendingcode.vehicle.model.PageResponse;
import pl.neverendingcode.vehicle.service.VehicleService;

import java.util.Collections;

@Slf4j
@Service
@AllArgsConstructor
public class MotorcycleServiceImpl implements VehicleService<Motorcycle> {

    private final VehicleRepositoryImpl<Motorcycle> motorcycleRepository;

    @Override
    public ResponseEntity<PageResponse<Motorcycle>> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Motorcycle> pageResult = motorcycleRepository.findAll(paging);

        if (pageResult.hasContent()) {
            return ResponseEntity.ok(new PageResponse<>(pageResult.getContent(), pageResult.getTotalPages()));
        } else {
            return ResponseEntity.ok(new PageResponse<>(Collections.emptyList(), 0));
        }
    }

    @Override
    public ResponseEntity<Motorcycle> fIndById(int id) {
        return motorcycleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MotorcycleNotFoundException("Motorcycle with id: " + id + " not found"));
    }

    @Override
    public ResponseEntity<Motorcycle> save(Motorcycle motorcycle) {
        return ResponseEntity.ok().body(motorcycleRepository.save(motorcycle));
    }

    @Override
    public ResponseEntity<?> remove(int id) {
        motorcycleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Motorcycle> update(int id, Motorcycle toUpdate) {
        Motorcycle result = motorcycleRepository.findById(id).map(motorcycle -> {
            motorcycle.updateFrom(toUpdate);
            return motorcycleRepository.save(motorcycle);
        }).orElseThrow(() -> new MotorcycleNotFoundException("Car with id: " + id + " not found"));
        log.info("Motorcycle with id: " + toUpdate.getId() + " has been updated successfully");
        return ResponseEntity.ok().body(result);
    }

}
