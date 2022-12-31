package pl.neverendingcode.service.car;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.neverendingcode.adapter.VehicleRepositoryImpl;
import pl.neverendingcode.entity.Car;
import pl.neverendingcode.exception.CarNotFoundException;
import pl.neverendingcode.model.PageResponse;
import pl.neverendingcode.service.VehicleService;

import java.net.URI;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements VehicleService<Car> {

    private final VehicleRepositoryImpl<Car> carRepository;

    @Override
    public ResponseEntity<PageResponse<Car>> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Car> pageResult = carRepository.findAll(paging);

        if (pageResult.hasContent()) {
            return ResponseEntity.ok(new PageResponse<>(pageResult.getContent(), pageResult.getTotalPages()));
        } else {
            return ResponseEntity.ok(new PageResponse<>(Collections.emptyList(), 0));
        }
    }

    @Override
    public ResponseEntity<Car> fIndById(int id) {
        return carRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
    }

    @Override
    public ResponseEntity<Car> save(Car car) {
        Car result = carRepository.save(car);
        return ResponseEntity.created(URI.create("/car/get/" + result.getId())).body(result);
    }

    @Override
    public ResponseEntity<?> remove(int id) {
        carRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Car> update(int id, Car toUpdate) {
        Car result = carRepository.findById(id).map(car -> {
            car.updateFrom(toUpdate);
            return carRepository.save(car);
        }).orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
        log.info("Car with id: " + toUpdate.getId() + " has been updated successfully");
        return ResponseEntity.ok().body(result);
    }

}
