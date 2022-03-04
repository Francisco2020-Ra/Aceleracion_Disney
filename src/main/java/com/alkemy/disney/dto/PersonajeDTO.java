package com.alkemy.disney.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PersonajeDTO {
    private Long id;

    @NotBlank(message ="El campo imagen  no puede estar vacío")
    private String imagen;
    @NotEmpty(message = "El campo nombre no debe estar vacio")
    @Length(max=20,message = "La longitud máxima del nombre es 20")
    private String nombre;

    private Integer edad;
    private Double peso;
    private String historia;


    private List<PeliculaDTO> peliculas;

}
