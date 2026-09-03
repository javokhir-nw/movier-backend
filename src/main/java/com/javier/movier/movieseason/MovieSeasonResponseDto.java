package com.javier.movier.movieseason;

import com.javier.movier.movie.MovieResponseDto;
import com.javier.movier.season.Season;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MovieSeasonResponseDto {
    private Long id;
    private Long seasonId;

    private String seasonName;
    private Integer orderNumber;
    private List<MovieResponseDto> episodes;


    public MovieSeasonResponseDto(MovieSeason ms){
        id = ms.getId();
        Season season = ms.getSeason();
        if (season != null){
            seasonId = season.getId();
            seasonName = season.getName();
            orderNumber = season.getOrderNumber();
        }
        episodes = ms.getEpisodes().stream().map(MovieResponseDto::new)
                .sorted(Comparator.comparing(MovieResponseDto::getOrderNumber))
                .toList();
    }
}
