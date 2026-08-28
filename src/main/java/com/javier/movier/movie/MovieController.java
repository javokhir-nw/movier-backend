package com.javier.movier.movie;

import com.javier.movier.utils.Pagination;
import com.javier.movier.utils.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;


    @PostMapping("/upsert")
    @PreAuthorize("hasAuthority('upsert movie')")
    public ResponseEntity<?> upsert(@RequestBody MovieRequestDto dto) {
        return ResponseEntity.ok(movieService.upsert(dto));
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody Pagination<Search> pagination){
        return ResponseEntity.ok(movieService.list(pagination));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        return ResponseEntity.ok(movieService.getById(id));
    }
}
