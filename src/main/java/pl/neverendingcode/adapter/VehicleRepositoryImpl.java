package pl.neverendingcode.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import pl.neverendingcode.entity.Vehicle;

import java.util.Optional;

@NoRepositoryBean
public interface VehicleRepositoryImpl<T extends Vehicle> {

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(int id);

    T save(T motorcycle);

    void deleteById(int id);

}
