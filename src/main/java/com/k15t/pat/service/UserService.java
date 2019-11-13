package com.k15t.pat.service;

import com.k15t.pat.exception.UserAlreadyExistsException;
import com.k15t.pat.model.User;

/**
 * User service handles all operations with users (in this case, just create new user)
 *
 * @author julianvasa
 */
public interface UserService {
    /**
     * Add a new user to the DB
     * @param user user to be created in the DB
     * @return User data
     * @throws UserAlreadyExistsException when a user already exists with the same name
     */
    User newUser(User user) throws UserAlreadyExistsException;
}
