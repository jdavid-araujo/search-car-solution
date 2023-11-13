package com.david.searchcarservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_poi")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Poi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private Double  raio;

    private Double  latitude;

    private Double  longitude;
}
