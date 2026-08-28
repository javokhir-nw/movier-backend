package com.javier.movier.watchhistory;

import com.javier.movier.movie.Movie;
import com.javier.movier.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "watch_history")
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Integer progressSeconds;

    private Boolean completed;
}