package com.epam.springcloud.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.springcloud.dao.UserDao;
import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user.UserDTO;
import com.epam.springcloud.resource.MessageBundle;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class SessionController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private User user;

    @PostMapping(path = { "/user/session" })
    public void createSession(@Validated UserDTO userDTO,
            BindingResult bindingResult, HttpSession session,
            HttpServletResponse response, Locale locale) throws Exception {
        log.debug("DTO from user: " + userDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User checkedUser = mapper.map(userDTO, User.class);
        log.debug("parsed user: " + checkedUser);
        User registredUser = userDao.getRegistredUser(checkedUser);
        log.debug("registred user: " + registredUser);
        if (registredUser != null) {
            BeanUtils.copyProperties(registredUser, this.user);
            log.debug("user in session: " + this.user);
        } else {
            String userMessage = messageSource.getMessage(
                    MessageBundle.INVALID_USER_MESSAGE, null, locale);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    Encode.forHtml(userMessage));
        }
    }

    @DeleteMapping(path = { "/user/session" })
    public void deleteSession(HttpSession session) {
        log.debug("user try to logout");
        session.invalidate();
    }

}
