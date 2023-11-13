package com.david.searchcarservice.unitTest;

import com.david.searchcarservice.exceptionhandler.SystemException;
import com.david.searchcarservice.model.Position;
import com.david.searchcarservice.model.Vehicle;
import com.david.searchcarservice.repository.PositionRepository;
import com.david.searchcarservice.repository.VehicleRepository;
import com.david.searchcarservice.service.PositionService;
import com.david.searchcarservice.service.VehicleService;
import com.david.searchcarservice.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class VehicleServiceImplTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private PositionService PositionService;

    @InjectMocks
    private VehicleServiceImpl vehicleService = new VehicleServiceImpl(vehicleRepository, PositionService);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testFindById() {
        // Mocking repository behavior
        Long vehicleId = 1L;
        Vehicle mockVehicle = new Vehicle();
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(mockVehicle));

        // Calling the service method
        Vehicle result = vehicleService.findById(vehicleId);

        // Verifying the result and interactions
        assertEquals(mockVehicle, result);
        verify(vehicleRepository, times(1)).findById(vehicleId);
    }

    @Test
    void testFindByIdWithNonExistingVehicle() {
        // Mocking repository behavior
        Long vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        // Calling the service method should throw an exception
        assertThrows(SystemException.class, () -> vehicleService.findById(vehicleId));

        // Verifying the interactions
        verify(vehicleRepository, times(1)).findById(vehicleId);
    }

    @Test
    void testFindAll() {
        // Mocking repository behavior
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        when(vehicleRepository.findAll()).thenReturn(Arrays.asList(vehicle1, vehicle2));

        // Calling the service method
        List<Vehicle> result = vehicleService.findAll();

        // Verifying the result and interactions
        assertEquals(2, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testSearch() {
        // Mocking repository behavior
        String placa = "ABC123";
        Pageable pageable = mock(Pageable.class);
        when(vehicleRepository.findByPlaca(placa, pageable)).thenReturn(Page.empty());

        // Calling the service method
        Page<Vehicle> result = vehicleService.search(placa, pageable);

        // Verifying the result and interactions
        assertNotNull(result);
        assertEquals(0, result.get().count());
        verify(vehicleRepository, times(1)).findByPlaca(placa, pageable);
    }

    @Test
    void testSavePosition() {
        // Mocking repository behavior
        Long vehicleId = 1L;
        Vehicle mockVehicle = mock(Vehicle.class);
        mockVehicle.setId(vehicleId);

        List<Position> positions = mock(List.class);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(mockVehicle));

        Position position = new Position();
        position.setVehicle(mockVehicle);

        // Calling the service method
        vehicleService.savePosition(vehicleId, position);

        // Verifying the interactions
        verify(vehicleRepository, times(1)).save(mockVehicle);
    }
}