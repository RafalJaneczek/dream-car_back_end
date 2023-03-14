package pl.neverendingcode.vehicle.contract;

import org.springframework.web.multipart.MultipartFile;
import pl.neverendingcode.core.entity.CarPhoto;
import pl.neverendingcode.vehicle.car.entity.Car;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface PhotoService {

    String SEPARATOR = "_";

    List<CarPhoto> preparePhotos(Car car, List<MultipartFile> files);

    default String prepareFileName(String mark, String model, int id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = LocalDateTime.now().format(formatter);
        return id + SEPARATOR + mark.toLowerCase() + SEPARATOR + model.toLowerCase() + SEPARATOR + currentDateTime;
    }

}
