package com.javier.movier.series;

import com.javier.movier.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {

    private final MovieService movieService;

    @PostMapping("upsert")
    @PreAuthorize("hasAuthority('upsert movie')")
    public ResponseEntity<?> upsert(@RequestBody SeriesRequestDto dto){
        return ResponseEntity.ok(movieService.upsertSeries(dto));
    }
}
