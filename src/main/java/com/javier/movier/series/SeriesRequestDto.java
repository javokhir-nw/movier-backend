package com.javier.movier.series;

import com.javier.movier.media.MediaRequestDto;
import com.javier.movier.movie.MovieType;
import lombok.Getter;
import lombok.Setter;

import static com.javier.movier.movie.MovieType.SERIES;

@Getter
@Setter
public class SeriesRequestDto extends MediaRequestDto {

    public MovieType getType(){
        return SERIES;
    }
}
