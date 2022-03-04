package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.basic.PeliculaBasicDTO;
import com.alkemy.disney.dto.filters.PeliculaFiltersDTO;
import com.alkemy.disney.entity.PeliculaEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.mapper.PeliculaMapper;
import com.alkemy.disney.respository.PeliculaRepository;
import com.alkemy.disney.respository.specification.PeliculaSpecification;
import com.alkemy.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private PeliculaMapper peliculaMapper;
    @Autowired
    private PeliculaSpecification peliculaSpecification;


    @Override
    public PeliculaDTO save(PeliculaDTO dto) {
        PeliculaEntity entity = peliculaMapper.peliculaDTO2Entity(dto);
        PeliculaEntity entitySaved = peliculaRepository.save(entity);
        PeliculaDTO resultado = peliculaMapper.peliculaEntity2DTO(entitySaved, true);
        return resultado;
    }

    @Override
    public List<PeliculaBasicDTO> getAllPeliculas() {
        List<PeliculaEntity> entity = peliculaRepository.findAll();
        List<PeliculaBasicDTO> dto = peliculaMapper.peliculaEntitySet2BasicDTOList(entity);
        return dto;
    }

    @Override
    public PeliculaDTO update(Long id, PeliculaDTO dto) {
        Optional<PeliculaEntity> entity = peliculaRepository.findById(id);
        PeliculaDTO peliculaDTO = new PeliculaDTO();
        if (!entity.isPresent()){
            throw new ParamNotFound("Id pelicula no valido");

        }else{
            PeliculaEntity entities = peliculaMapper.updatePelicula(entity, dto);
            peliculaDTO = peliculaMapper.peliculaEntity2DTO(entities, false);
            peliculaRepository.save(entities);
        }
        return peliculaDTO;
    }

    @Override
    public void delete(Long id) {
        peliculaRepository.deleteById(id);
    }


    public PeliculaDTO getDetailsById(Long id) {
        PeliculaEntity peliculaEntity = peliculaRepository.getById(id);
        PeliculaDTO peliculaDTO = peliculaMapper.peliculaEntity2DTO(peliculaEntity, true);
        return peliculaDTO;
    }

    @Override
    public PeliculaEntity getEntityById(Long idMovie) {
        return peliculaRepository.getById(idMovie);
    }


    @Override
    public List<PeliculaDTO> getByFilters(String name, Long idGenero, String order){
        PeliculaFiltersDTO filtersDTO = new PeliculaFiltersDTO(name, idGenero, order);
        List<PeliculaEntity> entities = peliculaRepository.findAll(peliculaSpecification.getByFilters(filtersDTO));
        List<PeliculaDTO> dtos = peliculaMapper.peliculaEntityList2DTOList(entities, true);
        return dtos;
    }


}
