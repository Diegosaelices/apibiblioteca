package com.example.apibiblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3)
    private String nombre;

    @ManyToMany(mappedBy = "autores")
    @JsonIgnoreProperties("autores")  // 👈 Esto rompe el ciclo de serialización
    private Set<Libro> libros = new HashSet<>();
}

