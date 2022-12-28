package pl.neverendingcode.model;

import pl.neverendingcode.entity.Vehicle;

import java.util.List;

public record PageResponse<T extends Vehicle>(List<T> vehicles, int totalPages) {}
