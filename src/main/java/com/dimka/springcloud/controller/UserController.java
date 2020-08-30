package com.dimka.springcloud.controller;

import com.dimka.springcloud.dao.UserDao;
import com.dimka.springcloud.dto.UserDTO;
import com.dimka.springcloud.entity.User;
import com.dimka.springcloud.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class UserController {

    private final UserDao userDao;
    private final User user;
    private final ModelMapper mapper;

    @PostMapping(path = { "/user" })
    public void registerUser(@Validated UserDTO userDTO,
                             BindingResult bindingResult) throws Exception {
        log.debug("user trys to registrate");
        log.debug("DTO from user: " + userDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User userToRegistrate = mapper.map(userDTO, User.class);
        log.debug("user to registrate " + userToRegistrate);
        User registredUser = userDao.registrateUser(userToRegistrate);
        if (registredUser == null) {
            throw new UserAlreadyExistsException(
                    "user already exists: " + userToRegistrate);
        }
        log.debug("user has been registred: " + registredUser);
        log.debug("loggining in user: " + registredUser);
        BeanUtils.copyProperties(registredUser, user);
        log.debug("user has been loggining in: " + registredUser);
    }
}
