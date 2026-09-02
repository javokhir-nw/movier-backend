package com.javier.movier.movie;

import com.javier.movier.category.CategoryDto;
import com.javier.movier.moviesource.MovieSourceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MovieResponseDto {
    private UUID id;
    private String title;
    private String description;
    private String imageUrl;
    private List<MovieSourceDto> sources;
    private List<CategoryDto> categories;

    public MovieResponseDto(Movie m) {
        this.id = m.getId();
        this.title = m.getTitle();
        this.description = m.getDescription();
        this.imageUrl = m.getImageUrl();
        this.categories = m.getCategories().stream().map(CategoryDto::new).toList();
        this.sources = m.getMovieSources().stream()
                .map(MovieSourceDto::new)
                .sorted(Comparator.comparing(MovieSourceDto::getOrderNumber))
                .toList();
    }
}
