package com.javier.movier.media;

import com.javier.movier.movie.MovieType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

import static com.javier.movier.movie.MovieType.MOVIE;

@Getter
@Setter
public class MediaRequestDto {
    private UUID id;
    private String title;
    private String description;
    private MovieType type = MOVIE;
    private String imageUrl;
    private Integer orderNumber;
    private Set<Long> categoryIds;
}
