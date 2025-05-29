package com.example.apibiblioteca.service;

import com.example.apibiblioteca.entities.*;
import com.example.apibiblioteca.repository.*;
import com.example.apibiblioteca.dto.LibroYNumEjemplaresDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;

@Service
public class LibroService {

    private final LibroRepository libroRepositorio;
    private final AutorRepository autorRepositorio;
    private final EjemplarRepository ejemplarRepositorio;

    public LibroService(LibroRepository libroRepositorio,
                        AutorRepository autorRepositorio,
                        EjemplarRepository ejemplarRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
        this.ejemplarRepositorio = ejemplarRepositorio;
    }

    public List<Libro> listarTodos(Pageable pageable) {
        return libroRepositorio.findAll(pageable).getContent();
    }

    public List<LibroYNumEjemplaresDTO> listarResumen() {
        return libroRepositorio.obtenerResumenLibros();
    }

    public Libro buscarPorId(Long id) {
        return libroRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Libro no encontrado con id: " + id));
    }

    public Libro guardar(Libro libro) {
        try {
            return libroRepositorio.save(libro);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("ISBN repetido");
        }
    }

    public void eliminar(Long id) {
        libroRepositorio.deleteById(id);
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepositorio.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Libro> buscarPorFecha(LocalDate fecha) {

        return libroRepositorio.findByFechaPublicacion(fecha);
    }

    public Libro agregarAutor(Long libroId, Long autorId) {
        Libro libro = buscarPorId(libroId);
        Autor autor = autorRepositorio.findById(autorId)
                .orElseThrow(() -> new NoSuchElementException("Autor no encontrado con id: " + autorId));
        libro.getAutores().add(autor);
        return libroRepositorio.save(libro);
    }

    public Libro quitarAutor(Long libroId, Long autorId) {
        Libro libro = buscarPorId(libroId);
        Autor autor = autorRepositorio.findById(autorId)
                .orElseThrow(() -> new NoSuchElementException("Autor no encontrado con id: " + autorId));
        libro.getAutores().remove(autor);
        return libroRepositorio.save(libro);
    }

    public Libro agregarEjemplar(Long libroId, Ejemplar ejemplar) {
        Libro libro = buscarPorId(libroId);
        ejemplar.setLibro(libro);
        libro.getEjemplares().add(ejemplar);
        return libroRepositorio.save(libro);
    }

    public Libro eliminarEjemplar(Long libroId, Long ejemplarId) {
        Libro libro = buscarPorId(libroId);
        Ejemplar ejemplar = ejemplarRepositorio.findById(ejemplarId)
                .orElseThrow(() -> new NoSuchElementException("Ejemplar no encontrado"));
        libro.getEjemplares().remove(ejemplar);
        ejemplarRepositorio.delete(ejemplar);
        return libroRepositorio.save(libro);
    }

    public List<Libro> buscarPorNombreAutor(String nombre) {
        return libroRepositorio.findByAutoresNombreContainingIgnoreCase(nombre);
    }

}
