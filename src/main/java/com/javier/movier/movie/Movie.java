package com.javier.movier.movie;

import com.javier.movier.actor.Actor;
import com.javier.movier.category.Category;
import com.javier.movier.comment.Comment;
import com.javier.movier.country.Country;
import com.javier.movier.movieseason.MovieSeason;
import com.javier.movier.moviesource.MovieSource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private Integer orderNumber;

    @Column(length = 5000)
    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private MovieType type = MovieType.MOVIE;

    @ManyToMany
    @JoinTable(
            name = "movies_categories",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "movie")
    private List<Comment> comments;

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MovieSource> movieSources = new ArrayList<>();

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Actor director;

    @ManyToMany
    @JoinTable(
            name = "movies_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    //todo for series

    @ManyToOne
    @JoinColumn(name = "parent_movie_id")
    private Movie parentMovie; // qaysi serialga tegishli (har doim, qism bo'lsa)

    @ManyToOne
    @JoinColumn(name = "movie_season_id")
    private MovieSeason movieSeason;

    @OneToMany(mappedBy = "parentMovie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movie> episodes = new ArrayList<>(); // faqat faslsiz seriallar uchun to'g'ridan-to'g'ri qismlar

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieSeason> movieSeasons = new ArrayList<>();
}
