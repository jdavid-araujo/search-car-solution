package com.david.searchcarservice.repository;

import com.david.searchcarservice.model.Poi;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {

    @Query("SELECT e FROM Poi e WHERE (:name IS NULL OR e.name like %:name%)")
    Page<Poi> findByName(@Param("name") String name, Pageable pageable);

}
