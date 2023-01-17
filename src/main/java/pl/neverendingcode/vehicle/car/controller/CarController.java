package pl.neverendingcode.vehicle.car.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.car.service.CarServiceImpl;
import pl.neverendingcode.vehicle.model.PageResponse;

@RestController
@RequestMapping(value = "/api/car")
@AllArgsConstructor
public class CarController {

    private final CarServiceImpl carServiceImpl;

    @GetMapping("/get/{id}")
    public ResponseEntity<Car> getCar(@PathVariable("id") int id) {
        return carServiceImpl.fIndById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<Car>> getCars(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "mark") String sortBy) {
        return carServiceImpl.findAll(pageNo, pageSize, sortBy);
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return carServiceImpl.save(car);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCar(@PathVariable("id") int id, @RequestBody Car car) {
        return carServiceImpl.update(id, car);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeCar(@PathVariable int id) {
        return carServiceImpl.remove(id);
    }

}
