package com.javier.movier.season;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
