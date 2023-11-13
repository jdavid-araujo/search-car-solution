package com.david.searchcarservice.service;

import com.david.searchcarservice.model.Position;
import com.david.searchcarservice.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService extends BaseService<Vehicle, Long> {

    void save(Vehicle entity);

    Page<Position> findAllByVehicleId(Long id, Pageable pageable);

    void savePosition(Long id, Position position);

    Page<Vehicle> search(String parameter, Pageable pageable);
}
