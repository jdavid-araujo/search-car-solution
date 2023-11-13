package com.david.searchcarservice.repository;

import com.david.searchcarservice.model.Vehicle;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("SELECT e FROM Vehicle e WHERE (:placa IS NULL OR e.placa like %:placa%)")
    Page<Vehicle> findByPlaca(@Param("placa") String placa, Pageable pageable);
}
