package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de Spring Data JPA para la entidad Autor.
 * Provee los métodos automáticos para interactuar con la base de datos (CRUD).
 */
public interface IAutorRepository extends JpaRepository<Autor, Long> {



}
