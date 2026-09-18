package com.javier.movier.utils;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageWrapper{
    long total;
    List<?> content;
}
