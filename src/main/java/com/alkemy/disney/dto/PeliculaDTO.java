package com.alkemy.disney.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class PeliculaDTO {


    private Long id;

    @NotBlank(message ="El campo imagen  no puede estar vacío")
    private String imagen;

    @NotEmpty(message = "El campo titulo no debe estar vacio")
    @Length(max=20,message = "La longitud máxima del titulo es 20")
    private String titulo;
    @Min(1)
    @Max(5)
    private Integer calificacion;

    private String fechaCreacion;

    @NotNull(message ="ID debe ser un número")
    private Long generoId;



    private GeneroDTO generoDTO;
    private List<PersonajeDTO> personajes;
}
