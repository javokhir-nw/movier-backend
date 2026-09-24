package com.javier.movier.movie;

import com.javier.movier.cache.ViewService;
import com.javier.movier.utils.Pagination;
import com.javier.movier.utils.Search;
import com.javier.movier.utils.UtilService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final UtilService utilService;
    private final ViewService viewService;


    @PostMapping("/upsert")
    @PreAuthorize("hasAuthority('upsert movie')")
    public ResponseEntity<?> upsert(@RequestBody MovieRequestDto dto) {
        return ResponseEntity.ok(movieService.upsertMovie(dto));
    }

    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody Pagination<Search> pagination){
        return ResponseEntity.ok(movieService.list(pagination));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id,
                                     HttpServletRequest request,
                                     HttpServletResponse response
                                     ){
        String visitorId = utilService.getOrCreateVisitorId(request, response);
        viewService.increment(visitorId,id.toString());

        return ResponseEntity.ok(movieService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('delete movie')")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}
