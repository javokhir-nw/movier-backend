package com.javier.movier.movie;

import com.javier.movier.moviesource.MovieSourceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.javier.movier.movie.Movie.MovieType.MOVIE;

@Getter
@Setter
public class MovieRequestDto {
    private UUID id;
    private UUID parentId;
    private String title;
    private String description;
    private int orderNumber;
    private String imageUrl;
    private Movie.MovieType type = MOVIE;
    private List<MovieSourceDto> sources;
    private Set<Long> categoryIds;
}
