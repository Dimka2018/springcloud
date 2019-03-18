package com.epam.springcloud.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.resource.LoggerAttributeCaretaker;
import com.epam.springcloud.resource.SessionAtributeCaretaker;

public class LoggingInterceptor  extends HandlerInterceptorAdapter{
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        MDC.put(LoggerAttributeCaretaker.SESSION_ID_KEY, request.getSession().getId());
        MDC.put(LoggerAttributeCaretaker.USER_KEY, (User) request.getSession().getAttribute(SessionAtributeCaretaker.USER_ATTRIBUTE_NAME));
        return true;
    }

}
