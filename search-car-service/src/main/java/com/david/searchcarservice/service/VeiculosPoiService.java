package com.david.searchcarservice.service;

import com.david.searchcarservice.modal.VehiclePoiTimeModal;
import com.david.searchcarservice.modal.VehicleDataSearchModal;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface VeiculosPoiService {

     List<VehiclePoiTimeModal> search(VehicleDataSearchModal vehicleDataSearchModal);

}