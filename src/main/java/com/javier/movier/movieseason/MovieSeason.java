package com.javier.movier.movieseason;

import com.javier.movier.movie.Movie;
import com.javier.movier.season.Season;
import com.javier.movier.source.Source;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movies_seasons")
public class MovieSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Movie series;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    @OneToMany(mappedBy = "movieSeason", cascade = CascadeType.PERSIST)
    private List<Movie> episodes = new ArrayList<>();
}