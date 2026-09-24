package com.javier.movier.country;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryDto {

    private Long id;
    private String name;
    private String code;

    public CountryDto(Country c) {
        this.id = c.getId();
        this.name = c.getName();
        this.code = c.getCode();
    }
}
