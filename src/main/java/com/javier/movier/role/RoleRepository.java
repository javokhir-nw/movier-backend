package com.javier.movier.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByCode(String code);

    @Query("select count(r.id) > 0 from Role r where lower(r.code) = lower(?1)")
    Boolean existsByCode(String code);
}
