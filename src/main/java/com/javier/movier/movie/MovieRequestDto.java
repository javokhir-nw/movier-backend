package com.javier.movier.movie;

import com.javier.movier.moviesource.MovieSourceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
public class MovieRequestDto {
    private UUID id;
    private UUID parentId;
    private String title;
    private String description;
    private int orderNumber;
    private String imageUrl;
    private List<MovieSourceDto> sources;
    private Set<Long> categoryIds;
}
