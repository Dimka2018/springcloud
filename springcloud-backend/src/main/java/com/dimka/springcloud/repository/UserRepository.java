package com.dimka.springcloud.repository;

import com.dimka.springcloud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findByLoginAndPassword(String login, String password);
}
