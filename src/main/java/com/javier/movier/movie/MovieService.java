package com.javier.movier.movie;

import com.javier.movier.actor.Actor;
import com.javier.movier.actor.ActorRepository;
import com.javier.movier.category.CategoryRepository;
import com.javier.movier.country.Country;
import com.javier.movier.country.CountryRepository;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    private final ActorRepository actorRepository;
    private final CountryRepository countryRepository;

    @Caching(evict = {
            @CacheEvict(value = "movies", key = "#dto.id",condition = "#dto.id != null"),
            @CacheEvict(value = "moviesList", allEntries = true)
    })
    @Transactional
    public UUID upsertMovie(MovieRequestDto dto) {
        Movie movie = findByIdOrElseCreate(dto.getId());
        setCommonFields(movie, dto);
        setSources(movie, dto.getSources());
        setCategories(movie, dto.getCategoryIds());
        setActors(movie, dto.getActorIds());
        setDirector(movie, dto.getDirectorId());
        setCountry(movie, dto.getCountryId());
        return saveMovie(movie);
    }

    @Caching(evict = {
            @CacheEvict(value = "movies", key = "#dto.id",condition = "#dto.id != null"),
            @CacheEvict(value = "moviesList", allEntries = true)
    })
    @Transactional
    public UUID upsertSeries(SeriesRequestDto dto) {
        Movie movie = findByIdOrElseCreate(dto.getId());
        setCommonFields(movie, dto);
        setCategories(movie, dto.getCategoryIds());
        setActors(movie, dto.getActorIds());
        setDirector(movie, dto.getDirectorId());
        setCountry(movie, dto.getCountryId());
        return saveMovie(movie);
    }

    @Caching(evict = {
            @CacheEvict(value = "movies", key = "#dto.parentMovieId"),
            @CacheEvict(value = "movies", key = "#dto.id",condition = "#dto.id != null"),
            @CacheEvict(value = "moviesList", allEntries = true)
    })
    @Transactional
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

    @Cacheable(
            value = "moviesList",
            key = "#pagination.page() + ':' + " +
                    "#pagination.size() + ':' + " +
                    "#pagination.search().value() + ':' + " +
                    "#pagination.search().categoryId() + ':' + " +
                    "#pagination.search().countryId() + ':' + " +
                    "#pagination.search().type()"
    )
    public PageWrapper list(Pagination<Search> pagination) {
        Search search = pagination.search();
        Page<MovieResponseDto> page = movieRepository.findAll(search.value(), search.categoryId(), search.type(), search.countryId(), PageRequest.of(pagination.page(), pagination.size()));
        return PageWrapper.builder().content(page.getContent()).total(page.getTotalElements()).build();
    }

    @Cacheable(value = "movies", key = "#id")
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

    @Caching(evict = {
            @CacheEvict(value = "movies", key = "#id"),
            @CacheEvict(value = "moviesList", allEntries = true)
    })
    @Transactional
    public void deleteMovie(UUID id) {
        if (!movieRepository.existsById(id)) {
            throw new EntityNotFoundException("Bunday kino mavjud emas!");
        }
        movieRepository.deleteById(id);
        log.info("Kino o'chirildi! ID: {}", id);
    }

    @Caching(evict = {
            @CacheEvict(value = "movies", key = "#id"),
            @CacheEvict(value = "moviesList", allEntries = true)
    })
    @Transactional
    public void deleteEpisode(UUID id) {
        Movie episode = findById(id);
        UUID parentId = episode.getParentMovie() != null ? episode.getParentMovie().getId() : null;
        movieRepository.deleteById(id);
        log.info("Qism o'chirildi! ID: {}", id);
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

    private void setCategories(Movie movie, Set<Long> categoryIds){
        if (categoryIds != null) {
            movie.setCategories(new HashSet<>(categoryRepository.findAllById(categoryIds)));
        }
    }

    private void setActors(Movie movie, Set<Long> actorIds) {
        if (actorIds != null) {
            movie.setActors(new HashSet<>(actorRepository.findAllById(actorIds)));
        }
    }

    private void setDirector(Movie movie, Long directorId) {
        if (directorId != null) {
            Actor director = actorRepository.findById(directorId)
                    .orElseThrow(() -> new EntityNotFoundException("Bunday rejissyor mavjud emas!"));
            movie.setDirector(director);
        } else {
            movie.setDirector(null);
        }
    }

    private void setCountry(Movie movie, Long countryId) {
        if (countryId != null) {
            Country country = countryRepository.findById(countryId)
                    .orElseThrow(() -> new EntityNotFoundException("Bunday mamlakat mavjud emas!"));
            movie.setCountry(country);
        } else {
            movie.setCountry(null);
        }
    }
}
