package com.david.searchcarservice.repository;

import com.david.searchcarservice.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByDataPosicaoBetweenAndVehicle_PlacaContaining(
            @Nullable LocalDateTime startDate,  @Nullable LocalDateTime endDate,  @Nullable String placa);

    Page<Position> findByVehicleId(Long vehicleId, Pageable pageable);
}

