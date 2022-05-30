package com.dimka.springcloud.service;

import com.dimka.springcloud.entity.User;
import com.dimka.springcloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> find(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }
}
