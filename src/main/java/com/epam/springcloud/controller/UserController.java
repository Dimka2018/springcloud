package com.epam.springcloud.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.springcloud.dao.UserDao;
import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user.UserDTO;
import com.epam.springcloud.resource.MessageBundle;
import com.epam.springcloud.resource.SessionAtributeCaretaker;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageSource messageSource;

    @PostMapping(path = { "/user" })
    public void registerUser(@Validated UserDTO userDTO,
            BindingResult bindingResult, HttpServletResponse response,
            HttpSession session, Locale locale) throws Exception {
        log.debug("DTO from user " + userDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User registredUser = new User(userDTO.getLogin(),
                userDTO.getPassword());
        log.debug("user to registrate " + registredUser);
        UserDTO registredUser = userDao.registrateUser(userDTO);
        if (registredUser != null) {
            session.setAttribute(SessionAtributeCaretaker.USER_ATTRIBUTE_NAME,
                    registredUser);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    messageSource.getMessage(MessageBundle.USER_EXISTS_MESSAGE,
                            null, locale));
        }
    }

}
