package pl.neverendingcode.vehicle.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.car.model.CarDTO;
import pl.neverendingcode.vehicle.motorcycle.entity.Motorcycle;
import pl.neverendingcode.vehicle.motorcycle.model.MotorcycleDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VehicleMapper {

    CarDTO carToCarDTO(Car car);

    MotorcycleDTO motorcycleToMotorcycleDTO(Motorcycle motorcycle);

}
