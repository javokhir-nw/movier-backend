package com.javier.movier.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/upsert")
    @PreAuthorize("hasAuthority('upsert category')")
    public ResponseEntity<?> upsert(@RequestBody CategoryDto categoryRequestDto){
        return ResponseEntity.ok(categoryService.upsert(categoryRequestDto));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(categoryService.list());
    }
}
