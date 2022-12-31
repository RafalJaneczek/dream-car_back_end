package pl.neverendingcode.service;

import org.springframework.http.ResponseEntity;
import pl.neverendingcode.entity.Vehicle;
import pl.neverendingcode.model.PageResponse;

public interface VehicleService<T extends Vehicle> {
    ResponseEntity<PageResponse<T>> findAll(Integer pageNo, Integer pageSize, String sortBy);

    ResponseEntity<T> fIndById(int id);

    ResponseEntity<T> save(T vehicle);

    ResponseEntity<?> remove(int id);

    ResponseEntity<T> update(int id, T toUpdate);
}
