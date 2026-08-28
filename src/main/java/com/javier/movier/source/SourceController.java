package com.javier.movier.source;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/source")
@RequiredArgsConstructor
public class SourceController {

    private final SourceService sourceService;

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(sourceService.list());
    }
}
