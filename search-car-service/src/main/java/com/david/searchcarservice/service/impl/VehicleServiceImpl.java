package com.david.searchcarservice.service.impl;

import com.david.searchcarservice.exceptionhandler.ResourceNotFoundException;
import com.david.searchcarservice.exceptionhandler.SystemException;
import com.david.searchcarservice.model.Position;
import com.david.searchcarservice.model.Vehicle;
import com.david.searchcarservice.repository.VehicleRepository;
import com.david.searchcarservice.service.PositionService;
import com.david.searchcarservice.service.VehicleService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;

    private PositionService positionService;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, PositionService positionService) {
        this.vehicleRepository = vehicleRepository;
        this.positionService = positionService;
    }

    /**
     * Find a vehicle by id
     * @param id The id of a vehicle
     * @return a Vehicle
     */
    @Cacheable(value = "vehicleCache", key = "#id")
    @Override
    public Vehicle findById(Long id) {
        return this.vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
    }

    /**
     * Return a list of all vehicles
     * @return List of vehicles
     */
    @Override
    public List<Vehicle> findAll() {
        return this.vehicleRepository.findAll();
    }

    /**
     * Return a list of vehicles filted by placa
     * @param placa A placa of a vehicle
     * @param pageable An object used to sort and page a result
     * @return
     */
    @Override
    public Page<Vehicle> search(String placa, Pageable pageable) {
        return this.vehicleRepository.findByPlaca(placa, pageable);
    }

    /**
     * Update a vehicle by id
     * @param id The id of the vehicle
     * @param entity The new data of vehicle
     * @return A vehicle of the updated data
     */
    @CachePut(value = "vehicleCache", key = "#id")
    @Override
    public Vehicle update(Long id, Vehicle entity) {
        Vehicle vehicle = this.findById(id);

        BeanUtils.copyProperties(entity, vehicle, "id", "positions");

        return this.vehicleRepository.save(vehicle);
    }

    /**
     * Sava a new vehicle
     * @param entity A vehicle
     */
    @Override
    public void save(Vehicle entity) {
        this.vehicleRepository.save(entity);
    }


    /**
     * Remove a vehicle by id
     * @param id The id of a vehicle
     */
    @CacheEvict(value = "vehicleCache", key = "#id")
    @Override
    public void deleteById(Long id) {
        if(!this.isExist(id)) {
            throw new ResourceNotFoundException("Resource does not exist id: " + id);
        }

        this.vehicleRepository.deleteById(id);
    }

    /**
     * Verify if a vehicle exist on database
     * @param id The id of a vehicle
     * @return true or false
     */
    @Override
    public boolean isExist(Long id) {
        return this.vehicleRepository.existsById(id);
    }

    /**
     * Return the position of a vehicle
     * @param id The id of an vehicle
     * @return A list of positions
     */
    @Override
    public Page<Position> findAllByVehicleId(Long id, Pageable pageable) {
        if(!this.isExist(id)) {
          throw   new SystemException("Resource does not exist id: " + id);
        }

        return this.positionService.findByVehicleId(id, pageable);
    }

    /**
     * Add a positon of a vehicle
     * @param id The id of a vehicle
     * @param position The position that need to be added
     */
    @Override
    public void savePosition(Long id, Position position) {
        Vehicle vehicle = this.findById(id);

        position.setVehicle(vehicle);

        vehicle.getPositions().add(position);

        this.vehicleRepository.save(vehicle);
    }
}
