package pl.neverendingcode.vehicle.model.dto;

import java.util.List;

public record PageResponse<T extends VehicleDTO>(List<T> vehicles, int totalPages) {
}
