package com.example.apibiblioteca.controller;

import com.example.apibiblioteca.entities.*;
import com.example.apibiblioteca.dto.LibroYNumEjemplaresDTO;
import com.example.apibiblioteca.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> listarLibros(Pageable pageable,
                                                    @RequestParam(required = false) String titulo,
                                                    @RequestParam(required = false) LocalDate fecha) {
        if (titulo != null) {
            return ResponseEntity.ok(libroService.buscarPorTitulo(titulo));
        }
        if (fecha != null) {
            return ResponseEntity.ok(libroService.buscarPorFecha(fecha));
        }
        return ResponseEntity.ok(libroService.listarTodos(pageable));
    }

    @GetMapping("/resumen")
    public ResponseEntity<List<LibroYNumEjemplaresDTO>> listarResumen() {
        return ResponseEntity.ok(libroService.listarResumen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Libro> crearLibro(@RequestBody @Valid Libro libro) {
        return ResponseEntity.ok(libroService.guardar(libro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{libroId}/autor/{autorId}")
    public ResponseEntity<Libro> agregarAutor(@PathVariable Long libroId, @PathVariable Long autorId) {
        return ResponseEntity.ok(libroService.agregarAutor(libroId, autorId));
    }

    @DeleteMapping("/{libroId}/autor/{autorId}")
    public ResponseEntity<Libro> quitarAutor(@PathVariable Long libroId, @PathVariable Long autorId) {
        return ResponseEntity.ok(libroService.quitarAutor(libroId, autorId));
    }

    @PostMapping("/{libroId}/ejemplares")
    public ResponseEntity<Libro> agregarEjemplar(@PathVariable Long libroId, @RequestBody @Valid Ejemplar ejemplar) {
        return ResponseEntity.ok(libroService.agregarEjemplar(libroId, ejemplar));
    }

    @DeleteMapping("/{libroId}/ejemplares/{ejemplarId}")
    public ResponseEntity<Libro> eliminarEjemplar(@PathVariable Long libroId, @PathVariable Long ejemplarId) {
        return ResponseEntity.ok(libroService.eliminarEjemplar(libroId, ejemplarId));
    }
    @GetMapping("/porAutor")
    public ResponseEntity<List<Libro>> buscarPorAutor(@RequestParam String nombre) {
        return ResponseEntity.ok(libroService.buscarPorNombreAutor(nombre));
    }

}
