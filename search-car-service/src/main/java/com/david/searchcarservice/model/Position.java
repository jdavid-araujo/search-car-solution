package com.david.searchcarservice.model;

import com.david.searchcarservice.enumeration.BooleanEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_position")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Position implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    private LocalDateTime dataPosicao;

    private Double  velociadde;

    private Double   longitude;

    private Double  latitude;

    @Enumerated(EnumType.STRING)
    private BooleanEnum ignicao;
}
