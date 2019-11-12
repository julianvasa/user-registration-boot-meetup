package com.k15t.pat.service;

import com.k15t.pat.model.User;
import com.k15t.pat.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService implements UserService {
    private final UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User newUser(User user) {
        return userRepository.save(user);
    }
}
