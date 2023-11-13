package com.david.searchcarservice.service;


import com.david.searchcarservice.model.Poi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PoiService extends BaseService<Poi, Long> {

    void save(Poi entity);

    Page<Poi> search(String name, Pageable pageable);
}
