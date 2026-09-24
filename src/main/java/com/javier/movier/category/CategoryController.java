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
        return ResponseEntity.status(201).body(categoryService.upsert(categoryRequestDto));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.status(200).body(categoryService.list());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('delete category')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
