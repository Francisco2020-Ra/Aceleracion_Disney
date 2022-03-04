package com.alkemy.disney.service;

import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.basic.PeliculaBasicDTO;
import com.alkemy.disney.entity.PeliculaEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PeliculaService {

    PeliculaDTO save(PeliculaDTO dto);

    List<PeliculaBasicDTO> getAllPeliculas();

    PeliculaDTO update(Long id, PeliculaDTO icon);

    void delete(Long id);

    PeliculaEntity getEntityById(Long idMovie);

    PeliculaDTO getDetailsById(Long id);

    List<PeliculaDTO> getByFilters(String name, Long idGenero, String order);





}
