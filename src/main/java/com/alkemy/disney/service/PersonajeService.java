package com.alkemy.disney.service;

import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.dto.basic.PersonajeBasicDTO;

import java.util.List;
import java.util.Set;

public interface PersonajeService {

    List<PersonajeBasicDTO> getAllPersonajes();

    PersonajeDTO save(PersonajeDTO iconDTO);

    PersonajeDTO update(Long id, PersonajeDTO personaje);

    void delete(Long id);

    PersonajeDTO getDetailsById(Long id);

    List<PersonajeDTO> getByFilters(String name, Integer edad, Set<Long> movies, String order);

    void addMovie(Long id, Long idMovie);

}
