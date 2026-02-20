package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de Spring Data JPA para la entidad Autor.
 * Provee los métodos automáticos para interactuar con la base de datos (CRUD).
 */
public interface IAutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Busca autores en la base de datos que estuvieran vivos en un año específico.
     * Utiliza Derived Queries de Spring Data JPA.
     *
     * @param fechaNacimiento  Año a consultar (debe ser menor o igual al año de nacimiento del autor).
     * @param fechaFallecimiento Año a consultar (debe ser mayor o igual al año de fallecimiento del autor).
     * @return Una lista de autores que cumplen con la condición.
     */
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(Integer fechaNacimiento, Integer fechaFallecimiento);

}
