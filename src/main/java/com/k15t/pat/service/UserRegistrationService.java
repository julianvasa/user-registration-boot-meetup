package com.k15t.pat.service;

import com.k15t.pat.exception.UserAlreadyExistsException;
import com.k15t.pat.model.User;
import com.k15t.pat.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User service handles all operations with users (in this case, just create new user)
 *
 * @author Julian Vasa
 */
@Service
public class UserRegistrationService implements UserService {
    /* Autowire userRepository, will forward user creation*/
    private final UserRepository userRepository;
    /* Autowire bCryptPasswordEncoder which wil lbe used to encode the password before storing in the DB */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRegistrationService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User newUser(User user) throws UserAlreadyExistsException {
        /* Check whether exists already an existing user with the same name */
        User alreadyExistUser = userRepository.findByName(user.getName());
        if (alreadyExistUser != null) {
            /* If already exists throw exception, will be handled by the controller */
            throw new UserAlreadyExistsException(user.getName());
        }
        /* There is no other user with the same name in the DB */
        else {
            /* Encrypt password for the new user */
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            /* Store the new user in the DB */
            return userRepository.save(user);
        }
    }
}
