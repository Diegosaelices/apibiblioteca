package com.example.apibiblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ejemplares")
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @NotNull
    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    @JsonIgnoreProperties("ejemplares")
    private Libro libro;
}

