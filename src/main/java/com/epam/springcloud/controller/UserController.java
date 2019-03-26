package com.epam.springcloud.controller;

import javax.validation.ValidationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.springcloud.dao.UserDao;
import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user.UserDTO;
import com.epam.springcloud.exception.UserAlreadyExistsException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private User user;

    @Autowired
    private ModelMapper mapper;

    @PostMapping(path = { "/user" })
    public void registerUser(@Validated UserDTO userDTO,
            BindingResult bindingResult) throws Exception {
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
        BeanUtils.copyProperties(registredUser, user);
    }

}
