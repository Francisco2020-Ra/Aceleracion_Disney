package com.alkemy.disney.service;

import com.alkemy.disney.dto.GeneroDTO;

import java.util.List;


public interface GeneroService {

    List<GeneroDTO> getAllGeneros();

    GeneroDTO save(GeneroDTO generoDTO);

    void delete(Long id);
}
