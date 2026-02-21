package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

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

    /**
     * Busca autores en la base de datos que estuvieran vivos en un año específico.
     * Utiliza JPQL (Java Persistence Query Language) para una sintaxis más limpia y legible.
     *
     * @param anio Año a consultar.
     * @return Una lista de autores que cumplen con la condición.
     */
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento >= :anio OR a.fechaFallecimiento IS NULL)")
    List<Autor> getAutoresVivosEnAnio(Integer anio);

    /**
     * Busca autores en la base de datos cuyo nombre contenga la cadena de texto
     * proporcionada, ignorando diferencias entre mayúsculas y minúsculas.
     * Utiliza Derived Queries de Spring Data JPA.
     *
     * @param nombre Cadena de texto a buscar dentro del nombre del autor.
     * @return Una lista de autores que coinciden con la búsqueda.
     */
    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca autores en la base de datos cuyo nombre contenga la cadena de texto
     * proporcionada, ignorando diferencias entre mayúsculas y minúsculas.
     * Utiliza JPQL (Java Persistence Query Language) con el operador ILIKE de PostgreSQL.
     *
     * @param nombre Cadena de texto a buscar dentro del nombre del autor.
     * @return Una lista de autores que coinciden con la búsqueda.
     */
    @Query("SELECT a FROM Autor a WHERE a.nombre ILIKE %:nombre%")
    List<Autor> getAutorPorNombre(String nombre);

    /**
     * Busca autores en la base de datos que hayan nacido en un año específico.
     * Utiliza Derived Queries de Spring Data JPA.
     *
     * @param anio Año exacto de nacimiento a consultar.
     * @return Una lista de autores nacidos en dicho año.
     */
    List<Autor> findByFechaNacimiento(Integer anio);

    /**
     * Busca autores en la base de datos que hayan nacido en un año específico.
     * Utiliza JPQL (Java Persistence Query Language) para realizar la consulta.
     *
     * @param anio Año exacto de nacimiento a consultar.
     * @return Una lista de autores nacidos en dicho año.
     */
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento = :anio")
    List<Autor> getAutoresPorFechaNacimiento(Integer anio);
}
