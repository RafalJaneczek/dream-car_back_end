package pl.neverendingcode.vehicle.motorcycle.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.neverendingcode.vehicle.motorcycle.entity.Motorcycle;
import pl.neverendingcode.vehicle.model.PageResponse;
import pl.neverendingcode.vehicle.motorcycle.service.MotorcycleServiceImpl;

@RestController
@RequestMapping(value = "/api//motorcycle")
@AllArgsConstructor
public class MotorcycleController {

    private final MotorcycleServiceImpl motorcycleServiceImpl;

    @GetMapping("/get/{id}")
    public ResponseEntity<Motorcycle> getMotorcycle(@PathVariable("id") int id) {
        return motorcycleServiceImpl.fIndById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<Motorcycle>> getMotorcycles(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                   @RequestParam(value = "sortBy", defaultValue = "mark") String sortBy) {
        return motorcycleServiceImpl.findAll(pageNo, pageSize, sortBy);
    }

    @PostMapping("/add")
    public ResponseEntity<Motorcycle> addMotorcycle(@RequestBody Motorcycle motorcycle) {
        return motorcycleServiceImpl.save(motorcycle);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Motorcycle> updateMotorcycle(@PathVariable("id") int id, @RequestBody Motorcycle motorcycle) {
        return motorcycleServiceImpl.update(id, motorcycle);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeMotorcycle(@PathVariable("id") int id) {
        return motorcycleServiceImpl.remove(id);
    }
}
