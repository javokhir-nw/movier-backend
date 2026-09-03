package com.javier.movier.source;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SourceService {

    private final SourceRepository sourceRepository;

    public List<SourceDto> list() {
        return sourceRepository.findAll()
                .stream().map(SourceDto::new)
                .sorted(Comparator.comparing(SourceDto::getOrderNumber))
                .toList();
    }
}
