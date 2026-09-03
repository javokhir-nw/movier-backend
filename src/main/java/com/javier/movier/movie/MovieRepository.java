package com.javier.movier.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    @Query("""
            select distinct new com.javier.movier.movie.MovieResponseDto(m)
            from Movie m
            left join m.categories c
            where (?1 is null or m.description ilike %?1% or m.title ilike %?1%)
            and (?2 is null or c.id = ?2)
            and (?3 is null or m.type = ?3)
            """)
    Page<MovieResponseDto> findAll(String value, Long categoryId, MovieType type, Pageable pageable);
}
