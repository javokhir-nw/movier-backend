package com.javier.movier.movie;

import com.javier.movier.media.MediaRequestDto;
import com.javier.movier.moviesource.MovieSourceDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.javier.movier.movie.MovieType.MOVIE;


@Getter
@Setter
public class MovieRequestDto extends MediaRequestDto {

    private List<MovieSourceDto> sources;

    public MovieType getType() {
        return MOVIE;
    }
}
