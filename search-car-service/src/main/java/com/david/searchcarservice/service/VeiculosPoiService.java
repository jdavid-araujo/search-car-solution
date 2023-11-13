package com.david.searchcarservice.service;

import com.david.searchcarservice.modal.PoiTimeModal;
import com.david.searchcarservice.modal.VehicleDataSearchModal;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface VeiculosPoiService {

     Map<String, List<PoiTimeModal>> search(VehicleDataSearchModal vehicleDataSearchModal);

}