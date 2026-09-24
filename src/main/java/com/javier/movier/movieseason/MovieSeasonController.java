package com.javier.movier.movieseason;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie-season")
public class MovieSeasonController {

    private final MovieSeasonService movieSeasonService;

    @PostMapping("/upsert")
    @PreAuthorize("hasAuthority('upsert movie')")
    public ResponseEntity<?> upsert(@RequestBody MovieSeasonRequestDto dto){
        return ResponseEntity.ok(movieSeasonService.upsert(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('delete movie')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        movieSeasonService.delete(id);
        return ResponseEntity.ok().build();
    }
}
