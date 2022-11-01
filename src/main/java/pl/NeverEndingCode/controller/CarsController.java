package pl.NeverEndingCode.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.NeverEndingCode.model.Car;
import pl.NeverEndingCode.service.CarService;

import java.util.List;

@RestController
@RequestMapping(value = "/car")
public class CarsController {

    private final CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Car> getCar(@PathVariable("id") int id) {
        return carService.findCar(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Car>> getCars() {
        return carService.findCars();
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCar(@PathVariable("id") int id, @RequestBody Car car) {
        return carService.updateCar(id, car);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Car> removeCar(@PathVariable int id) {
        return carService.removeCar(id);
    }

}
