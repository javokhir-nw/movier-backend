package com.javier.movier.season;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
