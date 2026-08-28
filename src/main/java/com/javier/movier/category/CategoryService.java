package com.javier.movier.category;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
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

    public List<CategoryDto> list() {
        return categoryRepository.findAll().stream().map(CategoryDto::new).toList();
    }
}
