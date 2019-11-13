package com.k15t.pat.repository;

import com.k15t.pat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data Repository to persist users in the database
 *
 * @author julianvasa
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Query DB for an existing user with the provided username
     * @param username the username to search for
     * @return the user with that username
     */
    User findByName (String username);
}
