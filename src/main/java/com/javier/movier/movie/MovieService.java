package com.javier.movier.movie;

import com.javier.movier.category.CategoryRepository;
import com.javier.movier.moviesource.MovieSource;
import com.javier.movier.moviesource.MovieSourceDto;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final SourceRepository sourceRepository;

    public String upsert(MovieRequestDto dto) {
        UUID id = dto.getId();
        Movie movie;
        if (id != null){
            movie = movieRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Bunday kino mavjud emas!"));
        } else {
            movie = new Movie();
        }
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setImageUrl(dto.getImageUrl());


        List<MovieSourceDto> sources = dto.getSources();
        if (sources != null && !sources.isEmpty()){
            List<MovieSource> movieSources = new ArrayList<>();
            for (MovieSourceDto s : sources){
                MovieSource ms = new MovieSource();
                ms.setMovie(movie);
                ms.setSource(sourceRepository.findById(s.getSourceId()).orElseThrow(() -> new EntityNotFoundException("Bunday source mavjud emas!")));
                ms.setUrl(s.getUrl());
                movieSources.add(ms);
            }
            movie.getMovieSources().clear();
            movie.getMovieSources().addAll(movieSources);
        }

        movie.setCategories(new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds())));
        movie = movieRepository.save(movie);

        UUID movieId = movie.getId();
        log.info("Kino muvaffaqqiyatli saqlandi! ID: {}", movieId);
        return movieId.toString();
    }

    public PageWrapper list(Pagination<Search> pagination) {
        Search search = pagination.search();
        Page<MovieResponseDto> page = movieRepository.findAll(search.value(),search.categoryId(), PageRequest.of(pagination.page(),pagination.size()));
        return PageWrapper.builder().content(page.getContent()).total(page.getTotalElements()).build();
    }

    public MovieResponseDto getById(UUID id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bunday film mavjud emas!"));
        return new MovieResponseDto(movie);
    }
}
