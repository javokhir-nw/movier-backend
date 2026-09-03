package com.javier.movier.movieseason;

import com.javier.movier.movie.Movie;
import com.javier.movier.movie.MovieRepository;
import com.javier.movier.season.Season;
import com.javier.movier.season.SeasonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieSeasonService {

    private final MovieSeasonRepository movieSeasonRepository;
    private final SeasonRepository seasonRepository;
    private final MovieRepository movieRepository;

    public Long upsert(MovieSeasonRequestDto dto) {
        Long id = dto.getId();
        MovieSeason movieSeason;
        if (id != null){
            movieSeason = movieSeasonRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Bunday mavsum mavjud emas!"));
        } else {
            movieSeason = new MovieSeason();
        }

        Long seasonId = dto.getSeasonId();
        Season season = seasonRepository.findById(seasonId).orElseThrow(
                () -> new EntityNotFoundException("Bunday mavsum mavjud emas!"));
        movieSeason.setSeason(season);

        UUID movieId = dto.getSeriesId();
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new EntityNotFoundException("Bunday film mavjud emas!"));
        movieSeason.setSeries(movie);
        movieSeason = movieSeasonRepository.save(movieSeason);

        return movieSeason.getId();
    }
}
