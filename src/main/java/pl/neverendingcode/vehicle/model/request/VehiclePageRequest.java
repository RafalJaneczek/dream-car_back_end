package pl.neverendingcode.vehicle.model.request;

public record VehiclePageRequest(Integer pageNo, Integer pageSize, String sortBy) {
    public VehiclePageRequest() {
        this(0, 5, "mark");
    }
}