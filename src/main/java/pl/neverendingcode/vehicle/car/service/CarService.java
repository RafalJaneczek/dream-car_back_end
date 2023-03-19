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
import pl.neverendingcode.vehicle.car.entity.CarPhoto;
import pl.neverendingcode.vehicle.car.exception.CarNotFoundException;
import pl.neverendingcode.vehicle.car.model.CarDTO;
import pl.neverendingcode.vehicle.contract.VehicleRepository;
import pl.neverendingcode.vehicle.contract.VehicleService;
import pl.neverendingcode.vehicle.mapper.VehicleMapper;
import pl.neverendingcode.vehicle.model.dto.PageResponse;
import pl.neverendingcode.vehicle.model.request.VehiclePageRequest;
import pl.neverendingcode.vehicle.service.VehiclePhotoService;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarService implements VehicleService<Car, CarDTO> {

    private final VehicleRepository<Car> carRepository;
    private final VehiclePhotoService vehiclePhotoService;
    private final VehicleMapper vehicleMapper;

    @Override
    public ResponseEntity<PageResponse<CarDTO>> findAll(VehiclePageRequest request) {
        Pageable paging = PageRequest.of(request.pageNo(), request.pageSize(), Sort.by(request.sortBy()));
        Page<CarDTO> pageResult = carRepository.findAll(paging).map(vehicleMapper::carToCarDTO);

        if (pageResult.hasContent()) {
            return ResponseEntity.ok(new PageResponse<>(pageResult.getContent(), pageResult.getTotalPages()));
        } else {
            return ResponseEntity.ok(new PageResponse<>(Collections.emptyList(), 0));
        }
    }

    @Override
    public ResponseEntity<CarDTO> findById(int id) {
        return carRepository.findById(id)
                .map(vehicleMapper::carToCarDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CarNotFoundException("Car with id: " + id + " not found"));
    }

    @Override
    public ResponseEntity<CarDTO> save(Car car, List<MultipartFile> files) {
        vehiclePhotoService.preparePhotos(car, files, CarPhoto.class).forEach(car::addPhoto);
        CarDTO result = vehicleMapper.carToCarDTO(carRepository.save(car));
        return ResponseEntity.created(URI.create("/car/get/" + result.getId())).body(result);
    }

    @Override
    public ResponseEntity<?> remove(int id) {
        carRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CarDTO> update(Car source, List<MultipartFile> files) {
        CarDTO result = carRepository.findById(source.getId().intValue()).map(car -> {
                    List<CarPhoto> carPhotos = vehiclePhotoService.preparePhotos(car, files, CarPhoto.class);
                    car.updateFrom(car, carPhotos);
                    return carRepository.save(car);
                })
                .map(vehicleMapper::carToCarDTO)
                .orElseThrow(() -> new CarNotFoundException("Car with id: " + source.getId() + " not found"));

        log.info("Car with id: " + source.getId() + " has been updated successfully");
        return ResponseEntity.ok().body(result);
    }

}
