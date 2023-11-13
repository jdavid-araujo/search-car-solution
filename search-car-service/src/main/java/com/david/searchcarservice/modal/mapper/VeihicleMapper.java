package com.david.searchcarservice.modal.mapper;

import com.david.searchcarservice.modal.VehicleModal;
import com.david.searchcarservice.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VeihicleMapper {

    VehicleModal sourceToTarget(Vehicle vehicle);

    @Mapping(target = "id", ignore = true)
    Vehicle targetToSource(VehicleModal vehicleModal);
}
