package pl.neverendingcode.vehicle.contract;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.vehicle.entity.Vehicle;
import pl.neverendingcode.vehicle.model.dto.VehicleDTO;
import pl.neverendingcode.vehicle.model.request.VehiclePageRequest;
import pl.neverendingcode.vehicle.model.dto.PageResponse;

import java.util.List;

public interface VehicleService<V extends Vehicle, D extends VehicleDTO> {

    ResponseEntity<PageResponse<D>> findAll(VehiclePageRequest request);

    ResponseEntity<D> findById(int id);

    ResponseEntity<D> save(V vehicle, List<MultipartFile> files);

    ResponseEntity<?> remove(int id);

    ResponseEntity<D> update(V vehicle, List<MultipartFile> files);

}
