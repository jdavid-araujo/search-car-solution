package com.david.searchcarservice.unitTest;

import com.david.searchcarservice.exceptionhandler.SystemException;
import com.david.searchcarservice.model.Poi;
import com.david.searchcarservice.repository.PoiRepository;
import com.david.searchcarservice.service.PoiService;
import com.david.searchcarservice.service.impl.PoiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PoServiceImplTests {

    @Mock
    private PoiRepository poiRepository;

    @InjectMocks
    private PoiService poiService = new PoiServiceImpl(poiRepository);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearch() {
        List<Poi> poiList = Arrays.asList(new Poi(), new Poi());
        Page<Poi> poiPage = new PageImpl<>(poiList);
        when(poiService.search(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(poiPage);


        // Calling the service method
        Page<Poi> result = poiService.search("TestPoi", PageRequest.of(0, 10));

        // Verifying the result
        assertEquals(poiPage, result);
        verify(poiRepository, times(1)).findByName("TestPoi", PageRequest.of(0, 10));
    }

    @Test
    void testUpdate() {
        // Mocking repository behavior
        Poi existingPoi = new Poi();
        when(poiRepository.findById(1L)).thenReturn(Optional.of(existingPoi));
        when(poiRepository.save(any(Poi.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Calling the service method
        Poi updatedPoi = new Poi();
        poiService.update(1L, updatedPoi);

        // Verifying the result
        verify(poiRepository, times(1)).findById(1L);
        verify(poiRepository, times(1)).save(existingPoi);
    }

    @Test
    void testUpdateNotFound() {
        // Mocking repository behavior
        when(poiRepository.findById(1L)).thenReturn(Optional.empty());

        // Calling the service method and expecting an exception
        assertThrows(SystemException.class, () -> poiService.update(1L, new Poi()));

        // Verifying the result
        verify(poiRepository, times(1)).findById(1L);
        verify(poiRepository, never()).save(any());
    }

    @Test
    void testDeleteById() {
        // Mocking repository behavior
        when(poiRepository.existsById(1L)).thenReturn(true);
        doNothing().when(poiRepository).deleteById(1L);

        // Calling the service method
        poiService.deleteById(1L);

        // Verifying the result
        verify(poiRepository, times(1)).existsById(1L);
        verify(poiRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdNotFound() {
        // Mocking repository behavior
        when(poiRepository.existsById(1L)).thenReturn(false);

        // Calling the service method and expecting an exception
        assertThrows(SystemException.class, () -> poiService.deleteById(1L));

        // Verifying the result
        verify(poiRepository, times(1)).existsById(1L);
        verify(poiRepository, never()).deleteById(1L);
    }

    @Test
    void testIsExist() {
        // Mocking repository behavior
        when(poiRepository.existsById(1L)).thenReturn(true);

        // Calling the service method
        boolean result = poiService.isExist(1L);

        // Verifying the result
        assertTrue(result);
        verify(poiRepository, times(1)).existsById(1L);
    }

    @Test
    void testIsNotExist() {
        // Mocking repository behavior
        when(poiRepository.existsById(1L)).thenReturn(false);

        // Calling the service method
        boolean result = poiService.isExist(1L);

        // Verifying the result
        assertFalse(result);
        verify(poiRepository, times(1)).existsById(1L);
    }
}
