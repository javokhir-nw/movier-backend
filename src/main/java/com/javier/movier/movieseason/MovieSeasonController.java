package com.javier.movier.movieseason;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
