package com.javier.movier.moviesource;

import com.javier.movier.movie.Movie;
import com.javier.movier.source.Source;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movies_sources")
public class MovieSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Source source;
}
