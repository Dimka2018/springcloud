package com.epam.springcloud.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.epam.springcloud.entity.user.User;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AutentificationIntercepter extends HandlerInterceptorAdapter {

    @Autowired
    private User user;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        log.debug("filtred user: " + user);
        if (user == null) {
            log.debug("redirect user");
            response.sendRedirect("welcome.html");
            return false;
        }
        return true;
    }

}
