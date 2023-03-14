package pl.neverendingcode.vehicle.contract;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.vehicle.entity.Vehicle;
import pl.neverendingcode.vehicle.model.PageResponse;

import java.util.List;

public interface VehicleService<T extends Vehicle> {

    ResponseEntity<PageResponse<T>> findAll(Integer pageNo, Integer pageSize, String sortBy);

    ResponseEntity<T> findById(int id);

    ResponseEntity<T> save(T vehicle, List<MultipartFile> files);

    ResponseEntity<?> remove(int id);

    ResponseEntity<T> update(int id, T updatedVehicle);

}
