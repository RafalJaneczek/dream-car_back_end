package pl.neverendingcode.helper;

import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.enums.VehicleCondition;
import pl.neverendingcode.vehicle.car.enums.BodyType;
import pl.neverendingcode.vehicle.car.enums.EngineType;

import java.math.BigInteger;
import java.util.List;

public class CarProviderHelper {

    public static Car getCar_1() {
        return Car.builder()
                .mark("LAMBORGHINI")
                .model("HURACAN")
                .engineCapacity(4879)
                .enginePower(675)
                .course(1500)
                .vehicleCondition(VehicleCondition.USED)
                .damaged(false)
                .productionYear(2021)
                .price(BigInteger.valueOf(1750000))
                .engineType(EngineType.GAS)
                .bodyType(BodyType.COUPE)
                .numberOfSeats(2).build();
    }

    public static Car getCar_2() {
        return Car.builder()
                .mark("FERRARI")
                .model("ENZO")
                .engineCapacity(5289)
                .enginePower(660)
                .course(25000)
                .vehicleCondition(VehicleCondition.USED)
                .damaged(false)
                .productionYear(2015)
                .price(BigInteger.valueOf(2500000))
                .engineType(EngineType.GAS)
                .bodyType(BodyType.COUPE)
                .numberOfSeats(2).build();
    }

    public static List<Car> getCarsList() {
        return List.of(getCar_1(), getCar_2());
    }

}
