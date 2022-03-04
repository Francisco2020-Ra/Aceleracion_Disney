package com.alkemy.disney.dto;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class GeneroDTO {

  private Long id;

  @NotEmpty(message = "El campo nombre no debe estar vacio")
  @Length(max=20,message = "La longitud máxima del nombre real es 20")
  private String nombre;

  @NotBlank(message ="El campo imagen  no puede estar vacío")
  private String imagen;



}
