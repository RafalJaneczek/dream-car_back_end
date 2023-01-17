package pl.neverendingcode.vehicle.model;

import pl.neverendingcode.vehicle.entity.Vehicle;

import java.util.List;

public record PageResponse<T extends Vehicle>(List<T> vehicles, int totalPages) {}
