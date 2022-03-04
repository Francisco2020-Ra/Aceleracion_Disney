package com.alkemy.disney.entity;


import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name= "genero")
@SQLDelete(sql = "UPDATE genero SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class GeneroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;

    private String imagen;

    private Boolean deleted = Boolean.FALSE;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "genero", cascade = CascadeType.MERGE)
    private List<PeliculaEntity> peliculaEntity;


}
