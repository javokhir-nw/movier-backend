package com.javier.movier.movie;

import com.javier.movier.actor.ActorDto;
import com.javier.movier.category.CategoryDto;
import com.javier.movier.country.CountryDto;
import com.javier.movier.movieseason.MovieSeasonResponseDto;
import com.javier.movier.moviesource.MovieSourceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MovieResponseDto {
    private UUID id;
    private String title;
    private String description;
    private String imageUrl;
    private Integer orderNumber;
    private MovieType type;
    private Integer viewCount = 0;
    private List<MovieSourceDto> sources;
    private List<CategoryDto> categories;
    private List<MovieResponseDto> episodes;
    private List<MovieSeasonResponseDto> seasons;
    private List<ActorDto> actors;
    private ActorDto director;
    private CountryDto country;
    private Date createdAt;

    public MovieResponseDto(Movie m) {
        this.id = m.getId();
        this.title = m.getTitle();
        this.viewCount = m.getViewCount();
        this.orderNumber = m.getOrderNumber();
        this.description = m.getDescription();
        this.type = m.getType();
        this.imageUrl = m.getImageUrl();
        this.categories = m.getCategories() != null
                ? m.getCategories().stream().map(CategoryDto::new).toList()
                : List.of();
        this.sources = m.getMovieSources().stream()
                .map(MovieSourceDto::new)
                .sorted(Comparator.comparing(MovieSourceDto::getOrderNumber))
                .toList();
        this.actors = m.getActors() != null
                ? m.getActors().stream().map(ActorDto::new).toList()
                : List.of();
        this.director = m.getDirector() != null ? new ActorDto(m.getDirector()) : null;
        this.country = m.getCountry() != null ? new CountryDto(m.getCountry()) : null;
        this.createdAt = m.getCreatedAt();
    }
}
