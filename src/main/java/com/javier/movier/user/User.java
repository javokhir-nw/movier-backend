package com.javier.movier.user;

import com.javier.movier.comment.Comment;
import com.javier.movier.movie.Movie;
import com.javier.movier.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String username;

    private String firstname;

    private String lastname;

    @Column(nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "favourite_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> favouriteMovies;
}
