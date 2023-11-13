package com.david.searchcarservice.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class VehiclePoiTimeModal {

    private Long idVehicle;

    private String vehicleName;

    private List<PoiTimeModal> poiTimeModals;
}
