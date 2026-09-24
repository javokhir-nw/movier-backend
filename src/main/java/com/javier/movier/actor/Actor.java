package com.javier.movier.actor;

import com.javier.movier.movie.Movie;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String about;

    private String imageUrl;


    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    @OneToMany(mappedBy = "director")
    private Set<Movie> directedMovies = new HashSet<>();
}
