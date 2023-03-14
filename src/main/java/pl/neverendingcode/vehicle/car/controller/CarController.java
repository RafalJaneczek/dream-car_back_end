package pl.neverendingcode.vehicle.car.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.core.annotation.CommunicationLog;
import pl.neverendingcode.core.helper.JsonObjectMapper;
import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.contract.VehicleService;
import pl.neverendingcode.vehicle.model.PageResponse;

import java.util.List;

@RestController
@RequestMapping(value = "/api/car")
@RequiredArgsConstructor
public class CarController {

    private final VehicleService<Car> carService;
    private final JsonObjectMapper jsonObjectMapper;

    @GetMapping("/get/{id}")
    @CommunicationLog
    public ResponseEntity<Car> getCar(@PathVariable("id") int id) {
        return carService.findById(id);
    }

    @GetMapping("/get-all")
    @CommunicationLog
    public ResponseEntity<PageResponse<Car>> getCars(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "mark") String sortBy) {
        return carService.findAll(pageNo, pageSize, sortBy);
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @CommunicationLog
    public ResponseEntity<Car> addCar(@RequestPart String car, @RequestPart("file") List<MultipartFile> files) {
        return carService.save(jsonObjectMapper.fromJSON(car, Car.class), files);
    }

    @PutMapping("/update/{id}")
    @CommunicationLog
    public ResponseEntity<?> updateCar(@PathVariable("id") int id, @RequestBody Car car) {
        return carService.update(id, car);
    }

    @DeleteMapping("/remove/{id}")
    @CommunicationLog()
    public ResponseEntity<?> removeCar(@PathVariable int id) {
        return carService.remove(id);
    }

}
