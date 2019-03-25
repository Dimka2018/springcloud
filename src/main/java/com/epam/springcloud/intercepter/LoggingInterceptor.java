package com.epam.springcloud.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.resource.LoggerAttributeCaretaker;

public class LoggingInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private User user;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        MDC.put(LoggerAttributeCaretaker.SESSION_ID_KEY,
                request.getSession().getId());
        MDC.put(LoggerAttributeCaretaker.USER_KEY, user);
        return true;
    }

}