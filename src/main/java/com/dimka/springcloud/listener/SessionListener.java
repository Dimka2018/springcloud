package com.dimka.springcloud.listener;

import com.dimka.springcloud.resource.LoggerAttribute;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Slf4j
@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        MDC.put(LoggerAttribute.SESSION_ID_KEY,
                sessionEvent.getSession().getId());
        log.debug("session create");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        log.debug("session destroy");
        MDC.put(LoggerAttribute.SESSION_ID_KEY, null);
        MDC.put(LoggerAttribute.USER_KEY, null);
    }

}
