package com.javier.movier.source;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/source")
@RequiredArgsConstructor
public class SourceController {

    private final SourceService sourceService;

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(sourceService.list());
    }

    @PostMapping("/upsert")
    @PreAuthorize("hasAuthority('upsert source')")
    public ResponseEntity<?> upsert(@RequestBody SourceDto dto){
        return ResponseEntity.ok(sourceService.upsert(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('delete source')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        sourceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
