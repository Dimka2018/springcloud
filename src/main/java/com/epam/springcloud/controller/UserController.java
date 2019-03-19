package com.epam.springcloud.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.springcloud.dao.UserDao;
import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.resource.MessageBundle;
import com.epam.springcloud.resource.SessionAtributeCaretaker;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageBundle messageBundle;

    @PostMapping(path = { "/user" })
    public void registerUser(@Validated User user, BindingResult bindingResult,
            HttpServletResponse response, HttpSession session, Locale locale)
            throws Exception {
        log.debug("user try to registrate: " + user);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User registredUser = userDao.registrateUser(user);
        if (registredUser != null) {
            session.setAttribute(SessionAtributeCaretaker.USER_ATTRIBUTE_NAME,
                    registredUser);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    messageBundle.getUserExistsMessage(locale));
        }
    }

}
