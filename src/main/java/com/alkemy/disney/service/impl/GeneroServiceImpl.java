package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.GeneroDTO;
import com.alkemy.disney.entity.GeneroEntity;
import com.alkemy.disney.mapper.GeneroMapper;
import com.alkemy.disney.respository.GeneroRepository;
import com.alkemy.disney.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private GeneroMapper generoMapper;

    @Override
    public List<GeneroDTO> getAllGeneros() {
        List<GeneroEntity> entity = generoRepository.findAll();
        List<GeneroDTO> dto = generoMapper.generoEntityList2DTOList(entity);
        return dto;
    }

    @Override
    public GeneroDTO save(GeneroDTO generoDTO) {
        GeneroEntity generoEntity = generoMapper.generoDTO2Entity(generoDTO);
        GeneroEntity generoSave = generoRepository.save(generoEntity);
        GeneroDTO dto = generoMapper.generoEntity2DTO(generoSave);
        return dto;
    }

    @Override
    public void delete(Long id) {
        generoRepository.deleteById(id);
    }
}
