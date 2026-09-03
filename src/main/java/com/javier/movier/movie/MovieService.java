package com.javier.movier.movie;

import com.javier.movier.category.CategoryRepository;
import com.javier.movier.episode.EpisodeRequestDto;
import com.javier.movier.media.MediaRequestDto;
import com.javier.movier.movieseason.MovieSeason;
import com.javier.movier.movieseason.MovieSeasonResponseDto;
import com.javier.movier.movieseason.MovieSeasonRepository;
import com.javier.movier.moviesource.MovieSource;
import com.javier.movier.moviesource.MovieSourceDto;
import com.javier.movier.series.SeriesRequestDto;
import com.javier.movier.source.SourceRepository;
import com.javier.movier.utils.PageWrapper;
import com.javier.movier.utils.Pagination;
import com.javier.movier.utils.Search;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.javier.movier.movie.MovieType.SERIES;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final SourceRepository sourceRepository;
    private final MovieSeasonRepository movieSeasonRepository;

    public UUID upsertMovie(MovieRequestDto dto) {
        Movie movie = findByIdOrElseCreate(dto.getId());
        setCommonFields(movie, dto);
        setSources(movie, dto.getSources());
        setCategories(movie,dto.getCategoryIds());
        return saveMovie(movie);
    }

    public UUID upsertSeries(SeriesRequestDto dto) {
        Movie movie = findByIdOrElseCreate(dto.getId());
        setCommonFields(movie, dto);
        setCategories(movie,dto.getCategoryIds());
        return saveMovie(movie);
    }

    public UUID upsertEpisodes(EpisodeRequestDto dto) {
        Movie movie = findByIdOrElseCreate(dto.getId());
        setCommonFields(movie, dto);
        setSources(movie, dto.getSources());

        UUID parentMovieId = dto.getParentMovieId();
        if (parentMovieId != null) {
            Movie parentMovie = findById(parentMovieId);
            movie.setParentMovie(parentMovie);
        }

        Long movieSeasonId = dto.getMovieSeasonId();
        if (movieSeasonId != null){
            movie.setMovieSeason(movieSeasonRepository.findById(movieSeasonId)
                    .orElseThrow(() -> new EntityNotFoundException("Bunday fasl mavjud emas!")));
        }

        return saveMovie(movie);
    }

    public PageWrapper list(Pagination<Search> pagination) {
        Search search = pagination.search();
        Page<MovieResponseDto> page = movieRepository.findAll(search.value(), search.categoryId(), search.type(), PageRequest.of(pagination.page(), pagination.size()));
        return PageWrapper.builder().content(page.getContent()).total(page.getTotalElements()).build();
    }

    public MovieResponseDto getById(UUID id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bunday film mavjud emas!"));
        MovieResponseDto dto = new MovieResponseDto(movie);
        if (movie.getType() == SERIES) {
            List<Movie> episodes = movie.getEpisodes();
            if (episodes != null && !episodes.isEmpty()) {
                dto.setEpisodes(episodes.stream().map(MovieResponseDto::new)
                        .sorted(Comparator.comparing(MovieResponseDto::getOrderNumber))
                        .toList());
            }
            List<MovieSeason> seasons = movie.getMovieSeasons();
            if (seasons != null) {
                dto.setSeasons(seasons.stream().map(MovieSeasonResponseDto::new).toList());
            }
        }
        return dto;
    }




    private void setCommonFields(Movie movie, MediaRequestDto dto) {
        UUID movieId = dto.getId();
        if (movieId != null) {
            movie.setId(movieId);
        }
        movie.setOrderNumber(dto.getOrderNumber());
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setType(dto.getType());
        movie.setImageUrl(dto.getImageUrl());
    }

    private UUID saveMovie(Movie movie) {
        movie = movieRepository.save(movie);
        UUID movieId = movie.getId();
        log.info("Kino muvaffaqqiyatli saqlandi! ID: {}", movieId);
        return movieId;
    }

    private Movie findByIdOrElseCreate(UUID id) {
        Movie movie;
        if (id != null) {
            movie = findById(id);
        } else {
            movie = new Movie();
        }
        return movie;
    }

    private Movie findById(UUID id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bunday kino mavjud emas!"));
    }

    private void setSources(Movie movie, List<MovieSourceDto> sources) {
        if (sources != null) {
            movie.getMovieSources().clear();
            for (MovieSourceDto s : sources) {
                MovieSource ms = new MovieSource();
                ms.setMovie(movie);
                ms.setSource(sourceRepository.findById(s.getSourceId()).orElseThrow(() -> new EntityNotFoundException("Bunday source mavjud emas!")));
                ms.setUrl(s.getUrl());
                movie.getMovieSources().add(ms);
            }
        }
    }

    private void setCategories(Movie movie,Set<Long> categoryIds){
        movie.setCategories(new HashSet<>(categoryRepository.findAllById(categoryIds)));
    }
}
