package com.javier.movier.country;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(countryService.list());
    }

    @PostMapping("/upsert")
    @PreAuthorize("hasAuthority('upsert country')")
    public ResponseEntity<?> upsert(@RequestBody CountryDto dto) {
        return ResponseEntity.ok(countryService.upsert(dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('delete country')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        countryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
