package org.mami.tasktracker.services;

import org.mami.tasktracker.domain.User;
import org.mami.tasktracker.exceptions.CustomFieldValidationException;
import org.mami.tasktracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public User registerUser(User newUser) {
        // username has to be unique
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            throw new CustomFieldValidationException(
                    "username",
                    "username already exists");
        }

        newUser.setPassword(this.bCryptPasswordEncoder.encode(newUser.getPassword()));
        return this.userRepository.save(newUser);
    }

    public void deleteUser(String id) {
        this.userRepository.deleteById(Long.valueOf(id));
    }


}
