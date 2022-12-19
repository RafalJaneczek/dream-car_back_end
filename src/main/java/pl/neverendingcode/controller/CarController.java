package pl.neverendingcode.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.neverendingcode.model.Car;
import pl.neverendingcode.model.PageResponse;
import pl.neverendingcode.service.car.CarServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/car")
@AllArgsConstructor
public class CarController {

    private final CarServiceImpl carServiceImpl;

    @GetMapping("/get/{id}")
    public ResponseEntity<Car> getCar(@PathVariable("id") int id) {
        return carServiceImpl.findCar(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<Car>> getCars(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "mark") String sortBy) {
        return carServiceImpl.findCars(pageNo, pageSize, sortBy);
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return carServiceImpl.saveCar(car);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCar(@PathVariable("id") int id, @RequestBody Car car) {
        return carServiceImpl.updateCar(id, car);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeCar(@PathVariable int id) {
        return carServiceImpl.removeCar(id);
    }

}
