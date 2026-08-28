package com.javier.movier.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class PageWrapper{
    long total;
    List<?> content;
}
