package com.david.searchcarservice.service.impl;

import com.david.searchcarservice.exceptionhandler.ResourceNotFoundException;
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
        return this.positionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + id));
    }

    /**
     * Return a list of all Position
     * @return List of Position
     */@Override
    public List<Position> findAll() {
        return this.positionRepository.findAll();
    }

    /**
     * Update a Position by id
     * @param id The id of the Position
     * @param entity The new data of Position
     * @return A Position of the updated data
     */@CachePut(value = "positionCache", key = "#id")
    @Override
    public Position update(Long id, Position entity) {
        Position position = this.findById(id);

        BeanUtils.copyProperties(entity, position, "id", "vehicle");

        return this.positionRepository.save(position);
    }

    /**
     * Remove a Position by id
     * @param id The id of a Position
     */@CacheEvict(value = "positionCache", key = "#id")
    @Override
    public void deleteById(Long id) {
        if(!this.isExist(id)) {
            throw new ResourceNotFoundException("Resource does not exist id: " + id);
        }

        this.positionRepository.deleteById(id);
    }

    /**
     * Verify if a Position exist on database
     * @param id The id of a Position
     * @return true or false
     */@Override
    public boolean isExist(Long id) {
        return this.positionRepository.existsById(id);
    }

    /**
     * Search a list of positions based on parameters
     * @param startDate
     * @param endDate
     * @param placa The value of a placa or part of that
     * @return
     */
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

    /**
     * Return a list of positions of a vehicle
     * @param vehicleId The id of a vehicle
     * @param pageable
     * @return A list of positions
     */
    @Override
    public Page<Position> findByVehicleId(Long vehicleId, Pageable pageable) {
        return this.positionRepository.findByVehicleId(vehicleId, pageable);
    }
}
