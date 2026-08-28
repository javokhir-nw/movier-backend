package com.javier.movier.utils;


public record Pagination<T>(
        int page,
        int size,
        T search
) {
}
