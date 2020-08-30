package com.dimka.springcloud.controller;

import com.dimka.springcloud.dao.UserDao;
import com.dimka.springcloud.dto.UserDTO;
import com.dimka.springcloud.entity.User;
import com.dimka.springcloud.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

@Slf4j
@AllArgsConstructor
@RestController
public class SessionController {

    private UserDao userDao;
    private ModelMapper mapper;
    private User user;


    @PostMapping(path = { "/user/session" })
    public void createSession(@Validated UserDTO userDTO,
                              BindingResult bindingResult) throws Exception {
        log.debug("user trys to log in");
        log.debug("DTO from user: " + userDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User checkedUser = mapper.map(userDTO, User.class);
        log.debug("parsed user: " + checkedUser);
        User registredUser = userDao.getRegistredUser(checkedUser);
        log.debug("registred user: " + registredUser);
        if (registredUser == null) {
            throw new UserNotFoundException(
                    "user does not exist: " + checkedUser);
        }
        BeanUtils.copyProperties(registredUser, this.user);
        log.debug("user has been loggining: " + registredUser);
    }

    @DeleteMapping(path = { "/user/session" })
    public void deleteSession(HttpSession session) throws Exception {
        log.debug("user try to logout");
        session.invalidate();
    }
}
