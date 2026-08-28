package com.javier.movier.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    @Query("select count(u.id) > 0 from User u where lower(u.username) = lower(?1) ")
    Boolean existsByUsername(String username);
}
