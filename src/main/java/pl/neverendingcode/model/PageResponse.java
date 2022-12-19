package pl.neverendingcode.model;

import java.util.List;

public record PageResponse<T extends Vehicle>(List<T> vehicles, int totalPages) {}
