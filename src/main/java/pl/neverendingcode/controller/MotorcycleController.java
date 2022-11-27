package pl.neverendingcode.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.neverendingcode.model.Motorcycle;
import pl.neverendingcode.service.MotorcycleService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/motorcycle")
@AllArgsConstructor
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Motorcycle> getMotorcycle(@PathVariable("id") int id) {
        return motorcycleService.findMotorcycle(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Motorcycle>> getMotorcycles() {
        return motorcycleService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Motorcycle> addMotorcycle(@RequestBody Motorcycle motorcycle) {
        return motorcycleService.saveMotorcycle(motorcycle);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Motorcycle> updateMotorcycle(@PathVariable("id") int id, @RequestBody Motorcycle motorcycle) {
        return motorcycleService.updateMotorcycle(id, motorcycle);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeMotorcycle(@PathVariable("id") int id) {
        return motorcycleService.removeMotorcycle(id);
    }
}
