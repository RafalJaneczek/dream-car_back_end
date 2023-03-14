package pl.neverendingcode.vehicle.car.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.car.exception.CarNotFoundException;
import pl.neverendingcode.vehicle.contract.VehicleRepository;
import pl.neverendingcode.vehicle.contract.VehicleService;
import pl.neverendingcode.vehicle.model.PageResponse;
import pl.neverendingcode.vehicle.service.CarPhotoService;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarService implements VehicleService<Car> {

    private final VehicleRepository<Car> carRepository;
    private final CarPhotoService photoService;

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
    public ResponseEntity<Car> findById(int id) {
        return carRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
    }

    @Override
    public ResponseEntity<Car> save(Car car, List<MultipartFile> files) {
        photoService.preparePhotos(car, files).forEach(car::addPhoto);
        Car result = carRepository.save(car);
        return ResponseEntity.created(URI.create("/car/get/" + result.getId())).body(result);
    }

    @Override
    public ResponseEntity<?> remove(int id) {
        carRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Car> update(int id, Car toUpdate) {
        Car result = carRepository.findById(id).map(car -> {
            car.updateFrom(toUpdate, car.getCarPhotos());
            return carRepository.save(car);
        }).orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
        log.info("Car with id: " + toUpdate.getId() + " has been updated successfully");
        return ResponseEntity.ok().body(result);
    }

}
