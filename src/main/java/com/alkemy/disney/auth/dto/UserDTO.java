package com.alkemy.disney.auth.dto;

import com.alkemy.disney.auth.entity.RoleEntity;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserDTO {

    @NotEmpty(message = "El campo usuario no debe estar en blanco")
    @Email(message = "debe ser una dirección de correo electrónico con formato correcto")
    private String username;
    @Size(min = 8)
    private String password;

    private Set<String> role;

}
