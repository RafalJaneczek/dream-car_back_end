package pl.neverendingcode.vehicle.motorcycle.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.vehicle.motorcycle.entity.Motorcycle;
import pl.neverendingcode.vehicle.model.PageResponse;
import pl.neverendingcode.vehicle.motorcycle.service.MotorcycleServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "/api//motorcycle")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleServiceImpl motorcycleServiceImpl;

    @GetMapping("/get/{id}")
    public ResponseEntity<Motorcycle> getMotorcycle(@PathVariable("id") int id) {
        return motorcycleServiceImpl.findById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<Motorcycle>> getMotorcycles(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                   @RequestParam(value = "sortBy", defaultValue = "mark") String sortBy) {
        return motorcycleServiceImpl.findAll(pageNo, pageSize, sortBy);
    }

    @PostMapping("/add")
    public ResponseEntity<Motorcycle> addMotorcycle(@RequestBody Motorcycle motorcycle, @RequestParam("file") List<MultipartFile> files) {
        return motorcycleServiceImpl.save(motorcycle, files);
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
