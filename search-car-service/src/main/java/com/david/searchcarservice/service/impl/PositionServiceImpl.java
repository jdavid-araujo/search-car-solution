package com.david.searchcarservice.service.impl;

import com.david.searchcarservice.exceptionhandler.SystemException;
import com.david.searchcarservice.model.Position;
import com.david.searchcarservice.repository.PositionRepository;
import com.david.searchcarservice.service.PositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    private PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Cacheable(value = "positionCache", key = "#id")
    @Override
    public Position findById(Long id) {
        return this.positionRepository.findById(id).orElseThrow(() -> new SystemException("Entity not found with id: " + id));
    }

    @Override
    public List<Position> findAll() {
        return this.positionRepository.findAll();
    }

    @CachePut(value = "positionCache", key = "#id")
    @Override
    public Position update(Long id, Position entity) {
        Position position = this.findById(id);

        BeanUtils.copyProperties(entity, position, "id", "vehicle");

        return this.positionRepository.save(position);
    }

    @CacheEvict(value = "positionCache", key = "#id")
    @Override
    public void deleteById(Long id) {
        if(!this.isExist(id)) {
            throw new SystemException("Resource does not exist id: " + id);
        }

        this.positionRepository.deleteById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return this.positionRepository.existsById(id);
    }

    @Override
    public List<Position> findPositionByDateAndVehicle(LocalDateTime startDate, LocalDateTime endDate, String placa) {
        if(ObjectUtils.isEmpty(startDate)) {
            startDate = LocalDateTime.now();
        }

        if(ObjectUtils.isEmpty(endDate)) {
            endDate = LocalDateTime.MIN;
        }
        return this.positionRepository.findByDataPosicaoBetweenAndVehicle_PlacaContaining(startDate, endDate, placa);
    }

    @Override
    public Page<Position> findByVehicleId(Long vehicleId, Pageable pageable) {
        return this.positionRepository.findByVehicleId(vehicleId, pageable);
    }
}
