package com.javier.movier.movieseason;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSeasonRepository extends JpaRepository<MovieSeason, Long> {
}
