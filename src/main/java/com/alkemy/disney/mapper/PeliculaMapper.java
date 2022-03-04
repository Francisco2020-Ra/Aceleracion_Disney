package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.GeneroDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.dto.basic.PeliculaBasicDTO;
import com.alkemy.disney.entity.GeneroEntity;
import com.alkemy.disney.entity.PeliculaEntity;
import com.alkemy.disney.entity.PersonajeEntity;
import com.alkemy.disney.respository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class PeliculaMapper {

    private PersonajeMapper personajeMapper;
    @Autowired
    private GeneroMapper generoMapper;
    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    public PeliculaMapper(@Lazy PersonajeMapper personajeMapper) {
        this.personajeMapper = personajeMapper;
    }

    public PeliculaEntity peliculaDTO2Entity(PeliculaDTO dto){
        PeliculaEntity entity = new PeliculaEntity();
        entity.setImagen(dto.getImagen());
        entity.setTitulo(dto.getTitulo());
        entity.setCalificacion(dto.getCalificacion());
        entity.setFechaDeCreacion(string2LocalDate(dto.getFechaCreacion()));
        entity.setGeneroId(dto.getGeneroId());
        Set<PersonajeEntity> listPersonajesEntity = personajeMapper.personajeDTOSet2EntityList(dto.getPersonajes());
        entity.setPersonajes(listPersonajesEntity);
        return entity;
    }

    private LocalDate string2LocalDate(String stringDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }

    public PeliculaDTO peliculaEntity2DTO(PeliculaEntity entity, boolean loadPersonajes){
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        dto.setTitulo(entity.getTitulo());
        dto.setCalificacion(entity.getCalificacion());
        dto.setFechaCreacion(entity.getFechaDeCreacion().toString());
        //Aca debe venir un Continente
        GeneroDTO generoDTO = generoMapper.generoEntity2DTO(opcional2Entity(entity));
        dto.setGeneroDTO(generoDTO);
        //El booleano me indica que quiero recuperar los iconos
        if(loadPersonajes){
            List<PersonajeDTO> personajeDTO = personajeMapper.personajeEntitySet2DTOList(entity.getPersonajes(), false);
            dto.setPersonajes(personajeDTO);
        }
        return dto;
    }

    public GeneroEntity opcional2Entity(PeliculaEntity entity){
        Optional<GeneroEntity> enti =  generoRepository.findById(entity.getGeneroId());
        GeneroEntity entiti = enti.get();
        return entiti;
    }

    public List<PeliculaDTO> peliculaEntityList2DTOList(List<PeliculaEntity> entities, boolean loadIcons){
        List<PeliculaDTO> dtos = new ArrayList();
        for(PeliculaEntity entity : entities){
            dtos.add(peliculaEntity2DTO(entity, loadIcons));
        }
        return dtos;
    }

    public List<PeliculaBasicDTO> peliculaEntitySet2BasicDTOList(Collection<PeliculaEntity> entities){
        List<PeliculaBasicDTO> dtos = new ArrayList<>();
        PeliculaBasicDTO basicDTO;
        for(PeliculaEntity entity: entities){
            basicDTO = new PeliculaBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setTitulo(entity.getTitulo());
            basicDTO.setImagen(entity.getImagen());
            basicDTO.setFechaCreacion(entity.getFechaDeCreacion().toString());
            dtos.add(basicDTO);
        }
        return dtos;
    }

    public PeliculaEntity peliculaDTO2EntityUpdate(PeliculaDTO dto){
        PeliculaEntity entity = new PeliculaEntity();
        entity.setImagen(dto.getImagen());
        entity.setTitulo(dto.getTitulo());
        entity.setCalificacion(dto.getCalificacion());
        entity.setFechaDeCreacion(string2LocalDate(dto.getFechaCreacion()));
        return entity;
    }

    public PeliculaEntity updatePelicula(Optional<PeliculaEntity> entity, PeliculaDTO dto){
        PeliculaEntity entities = entity.get();
        PeliculaEntity entity1 = peliculaDTO2EntityUpdate(dto);
        entities.setImagen(entity1.getImagen());
        entities.setTitulo(entity1.getTitulo());
        entities.setCalificacion(entity1.getCalificacion());
        entities.setFechaDeCreacion(entity1.getFechaDeCreacion());
        return entities;
    }

}
