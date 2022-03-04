package com.alkemy.disney.controller;


import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.dto.basic.PeliculaBasicDTO;
import com.alkemy.disney.mapper.PeliculaMapper;
import com.alkemy.disney.respository.PeliculaRepository;
import com.alkemy.disney.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("movies")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;
    @Autowired
    private PeliculaMapper peliculaMapper;
    @Autowired
    private PeliculaRepository peliculaRepository;

    //CRUD
    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PeliculaDTO> save(@Valid @RequestBody PeliculaDTO pelicula){
        PeliculaDTO peliculaGuardada =  peliculaService.save(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaGuardada);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<PeliculaBasicDTO>> getAll(){
        List<PeliculaBasicDTO> peliculas = peliculaService.getAllPeliculas();
        return ResponseEntity.ok().body(peliculas);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PeliculaDTO> update(@PathVariable Long id,@Valid @RequestBody PeliculaDTO peliculaDTO){
        PeliculaDTO result = peliculaService.update(id, peliculaDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        peliculaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //GetDetailsById
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PeliculaDTO> getDetailsById(@Valid @PathVariable Long id){
        PeliculaDTO pelicula = peliculaService.getDetailsById(id);
        return ResponseEntity.ok(pelicula);
    }

    //GetDetailsByFilters
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<PeliculaDTO>> getDetailsByFilters(@Valid
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long idGenero,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ){
        List<PeliculaDTO> movies = peliculaService.getByFilters(name, idGenero, order);
        return ResponseEntity.ok(movies);
    }

}
