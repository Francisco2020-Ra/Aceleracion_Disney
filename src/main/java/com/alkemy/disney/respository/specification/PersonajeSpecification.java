package com.alkemy.disney.respository.specification;


import com.alkemy.disney.dto.filters.PersonajeFiltersDTO;
import com.alkemy.disney.entity.PeliculaEntity;
import com.alkemy.disney.entity.PersonajeEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonajeSpecification {
    public Specification<PersonajeEntity> getByFilters(PersonajeFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {

            //Se importa la libreria de javax.persistence.criteria.Predicate
            List<Predicate> predicates = new ArrayList<>();

            //StringUtils.hasLength comprueba que la cadena que recibe no es nula ni de longitud 0
            if (StringUtils.hasLength(filtersDTO.getName())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nombre")),
                                "%" + filtersDTO.getName().toLowerCase() + "%"
                        )
                );
            }

            if (filtersDTO.getEdad() != null) {
                    predicates.add(
                            criteriaBuilder.equal(root.get("edad"),filtersDTO.getEdad())
                    );
            }

            if (!CollectionUtils.isEmpty(filtersDTO.getMovies())) {

                Join<PeliculaEntity, PersonajeEntity> join = root.join("personajes", JoinType.INNER);
                Expression<String> moviesId = join.get("id");
                predicates.add(moviesId.in(filtersDTO.getMovies()));

            }

            //Remove duplicates
            query.distinct(true);

            //Order resolver
            String orderByField = "nombre";
            query.orderBy(

                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
