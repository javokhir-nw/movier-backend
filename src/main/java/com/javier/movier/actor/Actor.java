package com.javier.movier.actor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    private Long id;

    private String name;

    private String about;
}
