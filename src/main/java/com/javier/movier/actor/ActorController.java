package com.javier.movier.actor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actor")
public class ActorController {

    private final ActorService actorService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(actorService.list());
    }

    @PostMapping("/upsert")
    @PreAuthorize("hasAuthority('upsert actor')")
    public ResponseEntity<?> upsert(@RequestBody ActorDto dto) {
        return ResponseEntity.ok(actorService.upsert(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('delete actor')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        actorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
