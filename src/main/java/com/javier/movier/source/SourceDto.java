package com.javier.movier.source;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SourceDto {
    private Long id;
    private String name;
    private int orderNumber;

    public SourceDto(Source s){
        id = s.getId();
        name = s.getName();
        orderNumber = s.getOrderNumber();
    }
}
