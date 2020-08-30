package com.dimka.springcloud.interceptor;

import com.dimka.springcloud.entity.User;
import com.dimka.springcloud.resource.LoggerAttribute;
import lombok.AllArgsConstructor;
import org.jboss.logging.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private final User user;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        MDC.put(LoggerAttribute.SESSION_ID_KEY,
                request.getSession().getId());
        MDC.put(LoggerAttribute.USER_KEY, user);
        return true;
    }

}