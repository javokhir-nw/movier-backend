package com.javier.movier.comment;

import com.javier.movier.movie.Movie;
import com.javier.movier.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String msg;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
