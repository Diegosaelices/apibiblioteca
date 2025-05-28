package com.example.apibiblioteca.repository;

import com.example.apibiblioteca.entities.Libro;
import com.example.apibiblioteca.dto.LibroYNumEjemplaresDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Buscar por título ignorando mayúsculas
    List<Libro> findByTituloContainingIgnoreCase(String titulo);


    //Buscar por fecha de publicación exacta
    List<Libro> findByFechaPublicacion(LocalDate fecha);


    // Número de ejemplares por libro
    @Query("SELECT new com.example.apibiblioteca.dto.LibroYNumEjemplaresDTO(" +
            "l.id, l.titulo, l.isbn, l.fechaPublicacion, COUNT(e)) " +
            "FROM Libro l LEFT JOIN l.ejemplares e " +
            "GROUP BY l.id, l.titulo, l.isbn, l.fechaPublicacion")
    List<LibroYNumEjemplaresDTO> obtenerResumenLibros();
}
