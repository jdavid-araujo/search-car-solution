package com.david.searchcarservice.service.impl;

import com.david.searchcarservice.exceptionhandler.ResourceNotFoundException;
import com.david.searchcarservice.exceptionhandler.SystemException;
import com.david.searchcarservice.model.Poi;
import com.david.searchcarservice.repository.PoiRepository;
import com.david.searchcarservice.service.PoiService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoiServiceImpl implements PoiService {

    private PoiRepository poiRepository;

    public PoiServiceImpl(PoiRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    @Cacheable(value = "poiCache", key = "#id")
    @Override
    public Poi findById(Long id) {
        return this.poiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
    }

    @Override
    public List<Poi> findAll() {
        return this.poiRepository.findAll();
    }

    @CachePut(value = "poiCache", key = "#id")
    @Override
    public Poi update(Long id, Poi entity) {
        Poi poi = this.findById(id);

        BeanUtils.copyProperties(entity, poi, "id");

        return this.poiRepository.save(poi);
    }

    @Override
    public void save(Poi entity) {
        this.poiRepository.save(entity);
    }

    @Override
    public Page<Poi> search(String name, Pageable pageable) {
        return this.poiRepository.findByName(name, pageable);
    }

    @CacheEvict(value = "poiCache", key = "#id")
    @Override
    public void deleteById(Long id) {
        if(!this.isExist(id)) {
            throw new ResourceNotFoundException("Resource does not exist id: " + id);
        }

        this.poiRepository.deleteById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return this.poiRepository.existsById(id);
    }

}
