package com.javier.movier.source;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceService {

    private final SourceRepository sourceRepository;

    @Cacheable(value = "sources")
    public List<SourceDto> list() {
        return sourceRepository.findAll()
                .stream().map(SourceDto::new)
                .sorted(Comparator.comparing(SourceDto::getOrderNumber))
                .toList();
    }

    @Transactional
    @CacheEvict(value = "sources", allEntries = true)
    public Long upsert(SourceDto dto) {
        Long id = dto.getId();
        Source source;
        if (id != null) {
            source = sourceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Bunday manba mavjud emas!"));
        } else {
            source = new Source();
        }
        source.setName(dto.getName());
        source.setOrderNumber(dto.getOrderNumber());
        source = sourceRepository.save(source);
        return source.getId();
    }

    @Transactional
    @CacheEvict(value = "sources", allEntries = true)
    public void delete(Long id) {
        if (!sourceRepository.existsById(id)) {
            throw new EntityNotFoundException("Bunday manba mavjud emas!");
        }
        sourceRepository.deleteById(id);
    }
}
