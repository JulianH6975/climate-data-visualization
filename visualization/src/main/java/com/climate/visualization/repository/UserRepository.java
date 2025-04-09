package com.climate.visualization.repository;

import com.climate.visualization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByRole(String role);

    @Query("SELECT u FROM User u WHERE SIZE(u.savedVisualizations) > 0")
    List<User> findUsersWithSavedVisualizations();
}
