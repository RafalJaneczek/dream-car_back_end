package pl.neverendingcode.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.neverendingcode.entity.Motorcycle;
import pl.neverendingcode.model.PageResponse;
import pl.neverendingcode.service.motorcycle.MotorcycleServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/motorcycle")
@AllArgsConstructor
public class MotorcycleController {

    private final MotorcycleServiceImpl motorcycleServiceImpl;

    @GetMapping("/get/{id}")
    public ResponseEntity<Motorcycle> getMotorcycle(@PathVariable("id") int id) {
        return motorcycleServiceImpl.findMotorcycle(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<Motorcycle>> getMotorcycles(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                   @RequestParam(value = "sortBy", defaultValue = "mark") String sortBy) {
        return motorcycleServiceImpl.findMotorcycles(pageNo, pageSize, sortBy);
    }

    @PostMapping("/add")
    public ResponseEntity<Motorcycle> addMotorcycle(@RequestBody Motorcycle motorcycle) {
        return motorcycleServiceImpl.saveMotorcycle(motorcycle);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Motorcycle> updateMotorcycle(@PathVariable("id") int id, @RequestBody Motorcycle motorcycle) {
        return motorcycleServiceImpl.updateMotorcycle(id, motorcycle);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeMotorcycle(@PathVariable("id") int id) {
        return motorcycleServiceImpl.removeMotorcycle(id);
    }
}
