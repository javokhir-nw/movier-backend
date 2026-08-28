package com.javier.movier.movie;

import com.javier.movier.category.Category;
import com.javier.movier.comment.Comment;
import com.javier.movier.moviesource.MovieSource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {

    public enum MovieType {
        MOVIE,
        SERIAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(length = 5000)
    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    public MovieType movieType = MovieType.MOVIE;

    private int orderNumber = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Movie parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movie> children = new ArrayList<>();

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
}
