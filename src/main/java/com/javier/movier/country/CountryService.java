package com.javier.movier.country;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public List<CountryDto> list() {
        return countryRepository.findAll().stream().map(CountryDto::new).toList();
    }

    @Transactional
    public Long upsert(CountryDto dto) {
        Long id = dto.getId();
        Country country;
        if (id != null) {
            country = countryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Bunday mamlakat mavjud emas!"));
        } else {
            country = new Country();
        }
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        country = countryRepository.save(country);
        log.info("Mamlakat muvaffaqqiyatli saqlandi! ID: {}", country.getId());
        return country.getId();
    }

    @Transactional
    public void delete(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new EntityNotFoundException("Bunday mamlakat mavjud emas!");
        }
        countryRepository.deleteById(id);
        log.info("Mamlakat o'chirildi! ID: {}", id);
    }

    public Country findById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bunday mamlakat mavjud emas!"));
    }
}
