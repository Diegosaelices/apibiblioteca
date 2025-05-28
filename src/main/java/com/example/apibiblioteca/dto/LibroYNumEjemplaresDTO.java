package com.example.apibiblioteca.dto;

import java.time.LocalDate;

public class LibroYNumEjemplaresDTO {

    private Long id;
    private String titulo;
    private String isbn;
    private LocalDate fechaPublicacion;
    private Long numeroEjemplares;

    public LibroYNumEjemplaresDTO(Long id, String titulo, String isbn, LocalDate fechaPublicacion, Long numeroEjemplares) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.numeroEjemplares = numeroEjemplares;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Long getNumeroEjemplares() {
        return numeroEjemplares;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setNumeroEjemplares(Long numeroEjemplares) {
        this.numeroEjemplares = numeroEjemplares;
    }
}
