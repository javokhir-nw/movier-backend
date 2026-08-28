package com.javier.movier.moviesource;

import com.javier.movier.source.Source;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovieSourceDto {
    private String url;
    private Long sourceId;

    private String sourceName;
    private int orderNumber;

    public MovieSourceDto(MovieSource ms){
        url = ms.getUrl();
        Source s = ms.getSource();
        if (s != null){
            sourceId = s.getId();
            sourceName = s.getName();
            orderNumber = s.getOrderNumber();
        }
    }
}
