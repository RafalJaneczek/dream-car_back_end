package pl.neverendingcode.vehicle.car.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.core.annotation.CommunicationLog;
import pl.neverendingcode.core.helper.JsonObjectMapper;
import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.car.model.CarDTO;
import pl.neverendingcode.vehicle.contract.VehicleService;
import pl.neverendingcode.vehicle.model.dto.PageResponse;
import pl.neverendingcode.vehicle.model.request.IdOnlyRequest;
import pl.neverendingcode.vehicle.model.request.VehiclePageRequest;

import java.util.List;

@RestController
@RequestMapping(value = "/api/car")
@RequiredArgsConstructor
public class CarController {

    private final VehicleService<Car, CarDTO> carService;
    private final JsonObjectMapper jsonObjectMapper;

    @GetMapping("/get")
    @CommunicationLog
    public ResponseEntity<CarDTO> getCar(@RequestBody IdOnlyRequest request) {
        return carService.findById(request.id());
    }

    @GetMapping("/get-all")
    @CommunicationLog
    public ResponseEntity<PageResponse<CarDTO>> getCars(@RequestBody VehiclePageRequest request) {
        return carService.findAll(request);
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @CommunicationLog
    public ResponseEntity<CarDTO> addCar(@RequestPart String car, @RequestPart("file") List<MultipartFile> files) {
        return carService.save(jsonObjectMapper.fromJSON(car, Car.class), files);
    }

    @PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @CommunicationLog
    public ResponseEntity<?> updateCar(@RequestPart String car, @RequestPart("file") List<MultipartFile> files) {
        return carService.update(jsonObjectMapper.fromJSON(car, Car.class), files);
    }

    @DeleteMapping("/remove")
    @CommunicationLog()
    public ResponseEntity<?> removeCar(@RequestBody IdOnlyRequest request) {
        return carService.remove(request.id());
    }

}
