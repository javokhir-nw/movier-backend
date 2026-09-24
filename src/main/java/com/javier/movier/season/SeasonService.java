package com.javier.movier.season;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {
    private final SeasonRepository seasonRepository;

    public List<SeasonDto> list() {
        return seasonRepository.findAll()
                .stream().map(SeasonDto::new)
                .toList();
    }

    @Transactional
    public Long upsert(SeasonDto dto) {
        Long id = dto.getId();
        Season season;
        if (id != null) {
            season = seasonRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Bunday mavsum mavjud emas!"));
        } else {
            season = new Season();
        }
        season.setName(dto.getName());
        season.setOrderNumber(dto.getOrderNumber());
        season = seasonRepository.save(season);
        return season.getId();
    }

    @Transactional
    public void delete(Long id) {
        if (!seasonRepository.existsById(id)) {
            throw new EntityNotFoundException("Bunday mavsum mavjud emas!");
        }
        seasonRepository.deleteById(id);
    }
}
