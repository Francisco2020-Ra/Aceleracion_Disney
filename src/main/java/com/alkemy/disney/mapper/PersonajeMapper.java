package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.dto.basic.PersonajeBasicDTO;
import com.alkemy.disney.entity.PersonajeEntity;
import com.alkemy.disney.exception.ParamNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PersonajeMapper {

    @Autowired
    private PeliculaMapper peliculaMapper;

    public PersonajeEntity personajeDTO2Entity(PersonajeDTO dto){
        PersonajeEntity entities = new PersonajeEntity();
        entities.setImagen(dto.getImagen());
        entities.setNombre(dto.getNombre());
        entities.setEdad(dto.getEdad());
        entities.setPeso(dto.getPeso());
        entities.setHistoria(dto.getHistoria());
        return entities;
    }

    public PersonajeDTO personajeEntity2DTO(PersonajeEntity entity, boolean loadPeliculas){
        PersonajeDTO dto = new PersonajeDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        dto.setNombre(entity.getNombre());
        dto.setEdad(entity.getEdad());
        dto.setPeso(entity.getPeso());
        dto.setHistoria(entity.getHistoria());
        //Lista de paises
        if(loadPeliculas){
            List<PeliculaDTO> peliculasDTO = peliculaMapper.peliculaEntityList2DTOList(entity.getPeliculas(), false);
            dto.setPeliculas(peliculasDTO);
        }
        return dto;
    }

    public List<PersonajeDTO> personajeEntitySet2DTOList(Collection<PersonajeEntity> entities, boolean loadPelicula) {
        List<PersonajeDTO> dtos = new ArrayList<>();
        for (PersonajeEntity entity : entities) {
            dtos.add(personajeEntity2DTO(entity, loadPelicula));
        }
        return dtos;
    }

    public List<PersonajeBasicDTO> personajeEntitySet2BasicDTOList(Collection<PersonajeEntity> entities){
        List<PersonajeBasicDTO> dtos = new ArrayList<>();
        PersonajeBasicDTO basicDTO;
        for(PersonajeEntity entity: entities){
            basicDTO = new PersonajeBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setNombre(entity.getNombre());
            basicDTO.setImagen(entity.getImagen());
            dtos.add(basicDTO);
        }
        return dtos;
    }

    public PersonajeEntity updatePersonaje(Optional<PersonajeEntity> entity, PersonajeDTO dto){
        PersonajeEntity entities = entity.get();
        PersonajeEntity entity1 = personajeDTO2Entity(dto);
        entities.setImagen(entity1.getImagen());
        entities.setNombre(entity1.getNombre());
        entities.setEdad(entity1.getEdad());
        entities.setPeso(entity1.getPeso());
        entities.setHistoria(entity1.getHistoria());
        return entities;
    }

    public Set<PersonajeEntity> personajeDTOSet2EntityList(List<PersonajeDTO> dtos) {
        Set<PersonajeEntity> entities = new HashSet<>();
        PersonajeEntity personajeEntity = new PersonajeEntity();
        for (PersonajeDTO personajeDTO : dtos) {
            personajeEntity.setImagen(personajeDTO.getImagen());
            personajeEntity.setNombre(personajeDTO.getNombre());
            personajeEntity.setEdad(personajeDTO.getEdad());
            personajeEntity.setPeso(personajeDTO.getPeso());
            personajeEntity.setHistoria(personajeDTO.getHistoria());
            entities.add(personajeEntity);
        }
        return entities;
    }

}
