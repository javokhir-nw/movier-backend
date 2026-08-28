package com.javier.movier.country;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class Country {

    @Id
    private Long id;

    private String name;

    private String code;
}
