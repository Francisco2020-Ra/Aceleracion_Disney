package com.alkemy.disney.mapper;


import com.alkemy.disney.dto.GeneroDTO;
import com.alkemy.disney.entity.GeneroEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GeneroMapper {

    //Transforma un dto a entity, para ingresarlo a la base de datos
    public GeneroEntity generoDTO2Entity(GeneroDTO dto){
        GeneroEntity generoEntity = new GeneroEntity();
        generoEntity.setNombre(dto.getNombre());
        generoEntity.setImagen(dto.getImagen());
        return generoEntity;
    }

    //Transforma un entity a dto, cuando el entity viene de la base de datos
    public GeneroDTO generoEntity2DTO(GeneroEntity entity){
        GeneroDTO generoDTO = new GeneroDTO();
        generoDTO.setId(entity.getId());
        generoDTO.setNombre(entity.getNombre());
        generoDTO.setImagen(entity.getImagen());
        return generoDTO;
    }

    public List<GeneroDTO> generoEntityList2DTOList(List<GeneroEntity> entities){
        List<GeneroDTO> dto = new ArrayList();
        for(GeneroEntity entity : entities){
            dto.add(generoEntity2DTO(entity));
        }
        return  dto;
    }
}
