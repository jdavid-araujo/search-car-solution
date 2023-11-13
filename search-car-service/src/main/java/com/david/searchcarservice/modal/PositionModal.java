package com.david.searchcarservice.modal;

import com.david.searchcarservice.enumeration.BooleanEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PositionModal {

    @NotNull
    private LocalDateTime dataPosicao;

    @NotNull
    private Double  velociadde;

    @NotNull
    private Double   longitude;

    @NotNull
    private Double  latitude;

    @NotNull
    private BooleanEnum ignicao;
}
