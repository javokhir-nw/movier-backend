package com.javier.movier.season;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/season")
@RequiredArgsConstructor
public class SeasonController {

    private final SeasonService seasonService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('list season')")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(seasonService.list());
    }

    @PostMapping("/upsert")
    @PreAuthorize("hasAuthority('upsert season')")
    public ResponseEntity<?> upsert(@RequestBody SeasonDto dto){
        return ResponseEntity.ok(seasonService.upsert(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('delete season')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        seasonService.delete(id);
        return ResponseEntity.ok().build();
    }
}
