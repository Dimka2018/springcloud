package com.dimka.springcloud.interceptor;

import com.dimka.springcloud.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@AllArgsConstructor
public class AuthentificationInterceptor extends HandlerInterceptorAdapter {

    private static final String REDIRECT_PAGE = "index.html";

    private final User user;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.debug("filtred user: " + user);
        if (user == null) {
            log.debug("redirect user");
            response.sendRedirect(REDIRECT_PAGE);
            return false;
        }
        return true;
    }

}
