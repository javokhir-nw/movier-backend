package com.javier.movier.utils;

import com.javier.movier.movie.MovieType;

public record Search(
        String value,
        Long categoryId,
        MovieType type
) {
}
