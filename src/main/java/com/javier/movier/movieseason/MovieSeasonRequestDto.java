package com.javier.movier.movieseason;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MovieSeasonRequestDto {
    private Long id;
    private Long seasonId;
    private UUID seriesId;
}
