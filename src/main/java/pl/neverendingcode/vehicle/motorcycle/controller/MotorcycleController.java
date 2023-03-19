package pl.neverendingcode.vehicle.motorcycle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.core.annotation.CommunicationLog;
import pl.neverendingcode.core.helper.JsonObjectMapper;
import pl.neverendingcode.vehicle.contract.VehicleService;
import pl.neverendingcode.vehicle.model.request.IdOnlyRequest;
import pl.neverendingcode.vehicle.model.request.VehiclePageRequest;
import pl.neverendingcode.vehicle.model.dto.PageResponse;
import pl.neverendingcode.vehicle.motorcycle.entity.Motorcycle;
import pl.neverendingcode.vehicle.motorcycle.model.MotorcycleDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/api//motorcycle")
@RequiredArgsConstructor
public class MotorcycleController {
    private final VehicleService<Motorcycle, MotorcycleDTO> motorcycleServiceImpl;
    private final JsonObjectMapper jsonObjectMapper;

    @GetMapping("/get")
    @CommunicationLog
    public ResponseEntity<MotorcycleDTO> getMotorcycle(@RequestBody IdOnlyRequest request) {
        return motorcycleServiceImpl.findById(request.id());
    }

    @GetMapping("/get-all")
    @CommunicationLog
    public ResponseEntity<PageResponse<MotorcycleDTO>> getMotorcycles(@RequestBody VehiclePageRequest request) {
        return motorcycleServiceImpl.findAll(request);
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @CommunicationLog
    public ResponseEntity<MotorcycleDTO> addMotorcycle(@RequestPart String motorcycle, @RequestPart("file") List<MultipartFile> files) {
        return motorcycleServiceImpl.save(jsonObjectMapper.fromJSON(motorcycle, Motorcycle.class), files);
    }

    @PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @CommunicationLog
    public ResponseEntity<?> updateMotorcycle(@RequestPart String motorcycle, @RequestPart("file") List<MultipartFile> files) {
        return motorcycleServiceImpl.update(jsonObjectMapper.fromJSON(motorcycle, Motorcycle.class), files);
    }

    @DeleteMapping("/remove")
    @CommunicationLog
    public ResponseEntity<?> removeMotorcycle(@RequestBody IdOnlyRequest request) {
        return motorcycleServiceImpl.remove(request.id());
    }
}
