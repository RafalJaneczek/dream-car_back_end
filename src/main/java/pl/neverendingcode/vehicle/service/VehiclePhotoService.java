package pl.neverendingcode.vehicle.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.core.entity.File;
import pl.neverendingcode.vehicle.contract.PhotoService;
import pl.neverendingcode.vehicle.entity.Vehicle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VehiclePhotoService implements PhotoService {

    @Override
    public <F extends File, V extends Vehicle> List<F> preparePhotos(V vehicle, List<MultipartFile> photos, Class<F> photoCLass) {
        List<F> vehiclePhotos = new ArrayList<>();
        photos.forEach(photo -> {
            String fileName = preparePhotoName(vehicle.getMark(), vehicle.getModel(), photos.indexOf(photo));
            try {
                F vehiclePhoto = photoCLass.getDeclaredConstructor().newInstance();
                vehiclePhoto.setFileName(fileName);
                vehiclePhoto.setFileData(photo.getBytes());
                vehiclePhotos.add(vehiclePhoto);
            } catch (IOException | ReflectiveOperationException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        });
        return vehiclePhotos;
    }

}
