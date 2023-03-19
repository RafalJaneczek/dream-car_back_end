package pl.neverendingcode.vehicle.contract;

import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.core.entity.File;
import pl.neverendingcode.vehicle.entity.Vehicle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface PhotoService {

    String SEPARATOR = "_";

    <F extends File, V extends Vehicle> List<F> preparePhotos(V vehicle, List<MultipartFile> photos, Class<F> photoCLass);

    default String preparePhotoName(String mark, String model, int id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = LocalDateTime.now().format(formatter);
        return id + SEPARATOR + mark.toLowerCase() + SEPARATOR + model.toLowerCase() + SEPARATOR + currentDateTime;
    }

}
