package com.alkemy.disney.controller;


import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.dto.basic.PersonajeBasicDTO;
import com.alkemy.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("characters")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    //CRUD
    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PersonajeDTO> save(@Valid @RequestBody PersonajeDTO personajeDTO){
        PersonajeDTO personajeGuardado =  personajeService.save(personajeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personajeGuardado);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<PersonajeBasicDTO>> getAll(){
        List<PersonajeBasicDTO> personajes = personajeService.getAllPersonajes();
        return ResponseEntity.ok().body(personajes);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PersonajeDTO> update(@PathVariable Long id,@Valid @RequestBody PersonajeDTO personajeDTO){
        PersonajeDTO result = personajeService.update(id, personajeDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        personajeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //DetailsById
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PersonajeDTO> getDetailsById(@PathVariable Long id){
        PersonajeDTO icon = personajeService.getDetailsById(id);
        return ResponseEntity.ok(icon);
    }

    //GetDetailsByFilters
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<PersonajeDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer edad,
            @RequestParam(required = false) Set<Long> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ){
        List<PersonajeDTO> personajes = personajeService.getByFilters(name, edad, movies, order);
        return ResponseEntity.ok(personajes);
    }

    //add
    @PostMapping("/{id}/movies/{idPelicula}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> addMovie(@PathVariable Long id, @PathVariable Long idPelicula){
        personajeService.addMovie(id, idPelicula);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
