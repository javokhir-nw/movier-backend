package com.javier.movier.episode;

import com.javier.movier.media.MediaRequestDto;
import com.javier.movier.movie.MovieType;
import com.javier.movier.moviesource.MovieSourceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import static com.javier.movier.movie.MovieType.EPISODE;

@Getter
@Setter
public class EpisodeRequestDto extends MediaRequestDto {

    private Integer orderNumber;
    private List<MovieSourceDto> sources;
    private UUID parentMovieId;
    private Long movieSeasonId;

    public MovieType getType(){
        return EPISODE;
    }
}
