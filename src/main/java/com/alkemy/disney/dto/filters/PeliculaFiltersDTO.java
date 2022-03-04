package com.alkemy.disney.dto.filters;

import lombok.Data;

@Data
public class PeliculaFiltersDTO {
    private String name;
    private Long idGenero;
    private String order;

    public PeliculaFiltersDTO(String name, Long idGenero, String order) {
        this.name = name;
        this.idGenero = idGenero;
        this.order = order;
    }

    public boolean isASC(){
        return order.compareToIgnoreCase("ASC") == 0;
    }
    public boolean isDESC(){
        return order.compareToIgnoreCase("DESC") == 0;
    }
}
