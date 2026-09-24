package com.javier.movier.episode;

import com.javier.movier.movie.MovieService;
import com.javier.movier.series.SeriesRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('delete movie')")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        movieService.deleteEpisode(id);
        return ResponseEntity.ok().build();
    }
}
