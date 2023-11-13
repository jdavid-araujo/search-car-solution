package com.david.searchcarservice.modal;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class VehicleModal {

    @NotBlank
    private String placa;
}
