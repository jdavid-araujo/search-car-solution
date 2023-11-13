package com.david.searchcarservice.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PlacaPoiTImeModal {

    private Long idPlaca;

    private List<PoiTimeModal> poiTimeModals;
}
