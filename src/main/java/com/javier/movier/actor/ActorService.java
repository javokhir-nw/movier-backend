package com.javier.movier.actor;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    public List<ActorDto> list() {
        return actorRepository.findAll().stream().map(ActorDto::new).toList();
    }

    @Transactional
    public Long upsert(ActorDto dto) {
        Long id = dto.getId();
        Actor actor;
        if (id != null) {
            actor = actorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Bunday aktyor mavjud emas!"));
        } else {
            actor = new Actor();
        }
        actor.setName(dto.getName());
        actor.setAbout(dto.getAbout());
        actor.setImageUrl(dto.getImageUrl());
        actor = actorRepository.save(actor);
        log.info("Aktyor muvaffaqqiyatli saqlandi! ID: {}", actor.getId());
        return actor.getId();
    }

    @Transactional
    public void delete(Long id) {
        if (!actorRepository.existsById(id)) {
            throw new EntityNotFoundException("Bunday aktyor mavjud emas!");
        }
        actorRepository.deleteById(id);
        log.info("Aktyor o'chirildi! ID: {}", id);
    }

    public Actor findById(Long id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bunday aktyor mavjud emas!"));
    }
}
