package com.david.searchcarservice.service;

import com.david.searchcarservice.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PositionService extends BaseService<Position, Long>  {
    List<Position> findPositionByDateAndVehicle(LocalDateTime startDate, LocalDateTime  endDate, String placa);

    Page<Position> findByVehicleId(Long vehicleId, Pageable pageable);

}
