package com.javier.movier.episode;

import com.javier.movier.movie.MovieService;
import com.javier.movier.series.SeriesRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/episode")
public class EpisodeController {

    private final MovieService movieService;

    @PostMapping("upsert")
    @PreAuthorize("hasAuthority('upsert movie')")
    public ResponseEntity<?> upsert(@RequestBody EpisodeRequestDto dto){
        return ResponseEntity.ok(movieService.upsertEpisodes(dto));
    }
}
