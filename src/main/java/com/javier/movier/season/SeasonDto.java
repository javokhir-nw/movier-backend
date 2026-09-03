package com.javier.movier.season;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeasonDto {

    private Long id;
    private String name;
    private Integer orderNumber;

    public SeasonDto(Season s){
        id = s.getId();
        name = s.getName();
        orderNumber = s.getOrderNumber();
    }
}
