package com.javier.movier.actor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActorDto {

    private Long id;
    private String name;
    private String about;
    private String imageUrl;

    public ActorDto(Actor a) {
        this.id = a.getId();
        this.name = a.getName();
        this.about = a.getAbout();
        this.imageUrl = a.getImageUrl();
    }
}
