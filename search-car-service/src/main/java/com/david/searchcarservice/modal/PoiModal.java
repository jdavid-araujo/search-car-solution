package com.david.searchcarservice.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class PoiModal {

    @NotBlank
    private String name;

    @NotNull
    private Double  raio;

    @NotNull
    private Double  latitude;

    @NotNull
    private Double  longitude;
}
