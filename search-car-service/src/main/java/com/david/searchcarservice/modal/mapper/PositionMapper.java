package com.david.searchcarservice.modal.mapper;

import com.david.searchcarservice.modal.PositionModal;
import com.david.searchcarservice.model.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionModal sourceToTarget(Position position);

    @Mapping(target = "id", ignore = true)
    Position targetToSource(PositionModal positionModal);
}
