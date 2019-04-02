package com.epam.springcloud.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.jboss.logging.MDC;

import com.epam.springcloud.resource.LoggerAttributeCaretaker;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        MDC.put(LoggerAttributeCaretaker.SESSION_ID_KEY,
                sessionEvent.getSession().getId());
        log.debug("session create");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        log.debug("session destroy");
    }

}
