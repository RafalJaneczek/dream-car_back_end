package pl.neverendingcode.vehicle.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.core.entity.CarPhoto;
import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.contract.PhotoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CarPhotoService implements PhotoService {

    @Override
    public List<CarPhoto> preparePhotos(Car car, List<MultipartFile> files) {
        List<CarPhoto> carPhotos = new ArrayList<>();
        files.forEach(file -> {
            String fileName = prepareFileName(car.getMark(), car.getModel(), files.indexOf(file));
            try {
                CarPhoto carPhoto = CarPhoto.builder()
                        .fileName(fileName)
                        .fileData(file.getBytes())
                        .build();
                carPhotos.add(carPhoto);
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        });
        return carPhotos;
    }

}
