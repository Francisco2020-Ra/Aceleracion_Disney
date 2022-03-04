package com.alkemy.disney.dto.filters;

import lombok.Data;

import java.util.Set;

@Data
public class PersonajeFiltersDTO {

    private String name;
    private Integer edad;
    private Set<Long> movies;
    private String order;

    public PersonajeFiltersDTO(String name, Integer edad, Set<Long> movies, String order) {
        this.name = name;
        this.edad = edad;
        this.movies = movies;
        this.order = order;
    }

    public boolean isASC(){
        return order.compareToIgnoreCase("ASC") == 0;
    }
    public boolean isDESC(){
        return order.compareToIgnoreCase("DESC") == 0;
    }


}
