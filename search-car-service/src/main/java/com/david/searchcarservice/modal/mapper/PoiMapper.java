package com.david.searchcarservice.modal.mapper;

import com.david.searchcarservice.modal.PoiModal;
import com.david.searchcarservice.model.Poi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PoiMapper {

    PoiModal sourceToTarget(Poi poi);

    @Mapping(target = "id", ignore = true)
    Poi targetToSource(PoiModal poiModal);
}
