package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.dto.filters.PersonajeFiltersDTO;
import com.alkemy.disney.dto.basic.PersonajeBasicDTO;
import com.alkemy.disney.entity.PeliculaEntity;
import com.alkemy.disney.entity.PersonajeEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.PersonajeMapper;
import com.alkemy.disney.respository.PersonajeRepository;
import com.alkemy.disney.respository.specification.PersonajeSpecification;
import com.alkemy.disney.service.PeliculaService;
import com.alkemy.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonaServiceImpl implements PersonajeService {
    @Autowired
    private PersonajeRepository personajeRepository;
    @Autowired
    private PersonajeMapper personajeMapper;
    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private PersonajeSpecification personajeSpecification;




    @Override
    public PersonajeDTO save(PersonajeDTO personajeDTO) {
        PersonajeEntity entity = personajeMapper.personajeDTO2Entity(personajeDTO);
        PersonajeEntity entitySaved = personajeRepository.save(entity);
        PersonajeDTO resultado = personajeMapper.personajeEntity2DTO(entitySaved, true);
        return resultado;
    }


    @Override
    public List<PersonajeBasicDTO> getAllPersonajes() {
        List<PersonajeEntity> entity = personajeRepository.findAll();
        List<PersonajeBasicDTO> dto = personajeMapper.personajeEntitySet2BasicDTOList(entity);
        return dto;
    }

    @Override
    public PersonajeDTO update(Long id, PersonajeDTO personaje) {
        Optional<PersonajeEntity> entity = personajeRepository.findById(id);
        PersonajeDTO personajeDTO = new PersonajeDTO();
        if (!entity.isPresent()){
            throw new ParamNotFound("Id personaje no valido");
        }
        PersonajeEntity entities = personajeMapper.updatePersonaje(entity, personaje);
        personajeDTO = personajeMapper.personajeEntity2DTO(entities, false);
        personajeRepository.save(entities);
        return personajeDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<PersonajeEntity> entity = personajeRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id personaje no valido");
        }
        personajeRepository.deleteById(id);
    }

    @Override
    public PersonajeDTO getDetailsById(Long id) {
        PersonajeEntity personajeEntity =  personajeRepository.getById(id);
        PersonajeDTO personajeDTO = personajeMapper.personajeEntity2DTO(personajeEntity, true);
        return personajeDTO;
    }

    @Override
    public List<PersonajeDTO> getByFilters(String name, Integer edad, Set<Long> movies, String order){
        PersonajeFiltersDTO filtersDTO = new PersonajeFiltersDTO(name, edad, movies, order);
        List<PersonajeEntity> entities = personajeRepository.findAll(personajeSpecification.getByFilters(filtersDTO));
        List<PersonajeDTO> dtos = personajeMapper.personajeEntitySet2DTOList(entities, true);
        return dtos;
    }


    @Override
    public void addMovie(Long id, Long idMovie){
        PersonajeEntity entity = personajeRepository.getById(id);
        entity.getPeliculas().size();
        PeliculaEntity peliculaEntity = peliculaService.getEntityById(idMovie);
        peliculaEntity.addPersonaje(entity);
        personajeRepository.save(entity);
    }




}
