package com.javier.movier.category;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @CacheEvict(value = "categories", allEntries = true)
    public String upsert(CategoryDto dto) {
        Long id = dto.getId();
        Category category;
        if (id != null) {
            category = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Bunday kategoriya mavjud emas!"));
        } else {
            category = new Category();
        }
        category.setName(dto.getName());
        category = categoryRepository.save(category);

        Long categoryId = category.getId();
        log.info("Kategoriya muvaffaqqiyatli saqlandi! ID: {}", categoryId);
        return categoryId.toString();
    }

    @Cacheable(value = "categories")
    public List<CategoryDto> list() {
        return categoryRepository.findAll().stream().map(CategoryDto::new).toList();
    }
}
