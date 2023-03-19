package pl.neverendingcode.vehicle.motorcycle.service;

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
import pl.neverendingcode.vehicle.contract.VehicleRepository;
import pl.neverendingcode.vehicle.contract.VehicleService;
import pl.neverendingcode.vehicle.mapper.VehicleMapper;
import pl.neverendingcode.vehicle.model.dto.PageResponse;
import pl.neverendingcode.vehicle.model.request.VehiclePageRequest;
import pl.neverendingcode.vehicle.motorcycle.entity.Motorcycle;
import pl.neverendingcode.vehicle.motorcycle.entity.MotorcyclePhoto;
import pl.neverendingcode.vehicle.motorcycle.exception.MotorcycleNotFoundException;
import pl.neverendingcode.vehicle.motorcycle.model.MotorcycleDTO;
import pl.neverendingcode.vehicle.service.VehiclePhotoService;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MotorcycleService implements VehicleService<Motorcycle, MotorcycleDTO> {

    private final VehicleRepository<Motorcycle> motorcycleRepository;
    private final VehiclePhotoService vehiclePhotoService;
    private final VehicleMapper vehicleMapper;

    @Override
    public ResponseEntity<PageResponse<MotorcycleDTO>> findAll(VehiclePageRequest request) {
        Pageable paging = PageRequest.of(request.pageNo(), request.pageSize(), Sort.by(request.sortBy()));
        Page<MotorcycleDTO> pageResult = motorcycleRepository.findAll(paging).map(vehicleMapper::motorcycleToMotorcycleDTO);

        if (pageResult.hasContent()) {
            return ResponseEntity.ok(new PageResponse<>(pageResult.getContent(), pageResult.getTotalPages()));
        } else {
            return ResponseEntity.ok(new PageResponse<>(Collections.emptyList(), 0));
        }
    }

    @Override
    public ResponseEntity<MotorcycleDTO> findById(int id) {
        return motorcycleRepository.findById(id)
                .map(vehicleMapper::motorcycleToMotorcycleDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MotorcycleNotFoundException("Motorcycle with id: " + id + " not found"));
    }

    @Override
    public ResponseEntity<MotorcycleDTO> save(Motorcycle motorcycle, List<MultipartFile> files) {
        vehiclePhotoService.preparePhotos(motorcycle, files, MotorcyclePhoto.class).forEach(motorcycle::addPhoto);
        MotorcycleDTO result = vehicleMapper.motorcycleToMotorcycleDTO(motorcycleRepository.save(motorcycle));
        return ResponseEntity.created(URI.create("/motorcycle/get/" + result.getId())).body(result);
    }

    @Override
    public ResponseEntity<?> remove(int id) {
        motorcycleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<MotorcycleDTO> update(Motorcycle source, List<MultipartFile> files) {
        MotorcycleDTO result = motorcycleRepository.findById(source.getId().intValue()).map(motorcycle -> {
                    List<MotorcyclePhoto> motorcyclePhotos = vehiclePhotoService.preparePhotos(motorcycle, files, MotorcyclePhoto.class);
                    motorcycle.updateFrom(source, motorcyclePhotos);
                    return motorcycleRepository.save(motorcycle);
                })
                .map(vehicleMapper::motorcycleToMotorcycleDTO)
                .orElseThrow(() -> new MotorcycleNotFoundException("Car with id: " + source.getId() + " not found"));

        log.info("Motorcycle with id: " + source.getId() + " has been updated successfully");
        return ResponseEntity.ok().body(result);
    }

}
